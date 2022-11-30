import React, { useState } from 'react';

export function useExportAsCsv(resultSet) {
	
	let firstRow = resultSet.columns;
	
	const rows = [
		["name1", "city1", "some other info"],
		["name2", "city2", "more info"]
	];

	let csvContent = "data:text/csv;charset=utf-8," 
		+ rows.map(e => e.join(",")).join("\n");
	
	var encodedUri = encodeURI(csvContent);
	var link = document.createElement("a");
	link.setAttribute("href", encodedUri);
	link.setAttribute("download", "my_data.csv");
	document.body.appendChild(link);
	link.click();
	document.body.removeChild(link);
	
	return ;
	
}
export default useExportAsCsv;
