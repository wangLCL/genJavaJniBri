/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.badlogic.gdx.jnigen;

import com.badlogic.gdx.jnigen.BuildTarget.TargetOs;
import com.badlogic.gdx.jnigen.FileDescriptor.FileType;

import java.util.ArrayList;

public class AndroidNdkScriptGenerator {
	public void generate (BuildConfig config, BuildTarget target) {
		if (target.os != TargetOs.Android) throw new IllegalArgumentException("target os must be Android");

		// create all the directories for outputing object files, shared libs and natives jar as well as build scripts.
		if (!config.libsDir.exists()) {
			if (!config.libsDir.mkdirs())
				throw new RuntimeException("Couldn't create directory for shared library files in '" + config.libsDir + "'");
		}
		if (!config.jniDir.exists()) {
			if (!config.jniDir.mkdirs())
				throw new RuntimeException("Couldn't create native code directory '" + config.jniDir + "'");
		}

		ArrayList<com.badlogic.gdx.jnigen.FileDescriptor> files = new ArrayList<com.badlogic.gdx.jnigen.FileDescriptor>();

		int idx = 0;
		String[] includes = new String[target.cIncludes.length + target.cppIncludes.length];
		for (String include : target.cIncludes)
			includes[idx++] = config.jniDir + "/" + include;
		for (String include : target.cppIncludes)
			includes[idx++] = config.jniDir + "/" + include;

		idx = 0;
		String[] excludes = new String[target.cExcludes.length + target.cppExcludes.length + 1];
		for (String exclude : target.cExcludes)
			excludes[idx++] = config.jniDir + "/" + exclude;
		for (String exclude : target.cppExcludes)
			excludes[idx++] = config.jniDir + "/" + exclude;
		//Include jniDir since ** does not match absolute files that start with /
		excludes[idx] = config.jniDir + "/**/target/*";

		gatherSourceFiles(config.jniDir, includes, excludes, files);

		// create Application.mk file
		String template = new com.badlogic.gdx.jnigen.FileDescriptor("com/badlogic/gdx/jnigen/resources/scripts/Application.mk.template",
			FileType.Classpath).readString();

		template = template.replace("%androidABIs%", String.join(" ", target.androidABIs));
		for(String extra : target.androidApplicationMk)
			template += "\n" + extra;

		config.jniDir.child("Application.mk").writeString(template, false);

		// create Android.mk file
		template = new com.badlogic.gdx.jnigen.FileDescriptor("com/badlogic/gdx/jnigen/resources/scripts/Android.mk.template", FileType.Classpath)
			.readString();

		StringBuilder srcFiles = new StringBuilder();
		for (int i = 0; i < files.size(); i++) {
			if (i > 0) srcFiles.append("\t");
			srcFiles.append(files.get(i).path().replace('\\', '/').replace(config.jniDir.toString() + "/", ""));
			if (i < files.size() - 1)
				srcFiles.append("\\\n");
			else
				srcFiles.append("\n");
		}

		StringBuilder headerDirs = new StringBuilder();
		for (String headerDir : target.headerDirs) {
			headerDirs.append(headerDir);
			headerDirs.append(" ");
		}

		template = template.replace("%sharedLibName%", config.sharedLibName);
		template = template.replace("%headerDirs%", headerDirs);
		template = template.replace("%cFlags%", target.cFlags);
		template = template.replace("%cppFlags%", target.cppFlags);
		template = template.replace("%linkerFlags%", target.linkerFlags);
		template = template.replace("%srcFiles%", srcFiles);
		for(String extra : target.androidAndroidMk)
			template += "\n" + extra;

		config.jniDir.child("Android.mk").writeString(template, false);
	}

	private void gatherSourceFiles (com.badlogic.gdx.jnigen.FileDescriptor file, String[] includes, String[] excludes, ArrayList<com.badlogic.gdx.jnigen.FileDescriptor> files) {
		String fileName = file.path().replace('\\', '/');
		if (file.isDirectory()) {
			if (match(fileName, excludes)) return;
			for (FileDescriptor child : file.list()) {
				gatherSourceFiles(child, includes, excludes, files);
			}
		} else {
			if (match(fileName, includes) && !match(fileName, excludes)) files.add(file);
		}
	}

	private boolean match (String file, String[] patterns) {
		return new AntPathMatcher().match(file, patterns);
	}
}
