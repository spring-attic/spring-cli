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

import java.util.List;
import java.util.stream.Stream;

import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.Table;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;

/**
 * Utility class for building tables to display data in the CLI.
 */
public final class TableUtils {

	// Private constructor to prevent instantiation
	private TableUtils() {
		throw new AssertionError("Utility class - do not instantiate");
	}

	/**
	 * Builds a table with a single-column layout for displaying a list of items.
	 * @param data the list of items to display in the table
	 * @param header the header for the table column
	 * @return a formatted Table object
	 */
	public static Table buildTable(List<String> data, String header) {
		Stream<String[]> headerStream = Stream.<String[]>of(new String[] { header });
		Stream<String[]> rows = (data != null) ? data.stream().map(item -> new String[] { item }) : Stream.empty();
		String[][] tableData = Stream.concat(headerStream, rows).toArray(String[][]::new);
		TableModel model = new ArrayTableModel(tableData);
		TableBuilder tableBuilder = new TableBuilder(model);
		return tableBuilder.addFullBorder(BorderStyle.fancy_light).build();
	}

}
