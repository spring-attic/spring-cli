/*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cli.util;

import java.io.StringWriter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.JAXB;

import org.apache.maven.model.Dependency;
import org.codehaus.plexus.util.xml.Xpp3Dom;

/**
 * @author Oleg Zhurakousky
 */
public final class ConversionUtils {

	private ConversionUtils() {

	}

	/**
	 * Converts Xpp3Dom to a String without any xml declaration
	 * @param dom the Xpp3Dom to convert
	 * @return String without any Xml Declarations
	 */
	public static String fromDomToString(Xpp3Dom dom) {
		String element = dom.toString();
        return element.lines().filter(l -> !l.contains("<?xml")).collect(Collectors.joining("\n"));
	}

	/**
	 * Formats a list of Maven Dependencies as a String without the xml declaration
	 * @param dependencies the list of Maven Dependencies to convert
	 * @return String without any Xml Declarations or optional and type elements if they contain default values
	 */
	public static String fromDependencyListToString(List<Dependency> dependencies) {
		StringWriter sw = new StringWriter();

		Dependencies deps = new Dependencies(dependencies);
		JAXB.marshal(deps, sw);
		String xmlString = sw.toString();
		// filter out lines containing xml declarations and default elements like:
		// <optional>false</optional> and <type>jar</type>
		return xmlString.lines().filter(l -> !l.contains("<?xml") &&
				!l.contains("<optional>false</optional>") &&
				!l.contains("<type>jar</type>"))
				.collect(Collectors.joining("\n"));
	}

	/*
	 * IMPORTANT: Because of JAXB, this class must be public and have public setters and
	 * getters, to produce proper <dependencies> XML segment.
	 */
	public static class Dependencies {

		private List<Dependency> dependency;

		public Dependencies(List<Dependency> dependencies) {
			this.dependency = dependencies;
		}

		public List<Dependency> getDependency() {
			return dependency;
		}

		public void setDependency(List<Dependency> dependencies) {
			this.dependency = dependencies;
		}

	}

}
