import React, { useState } from 'react';

import Pagination from 'react-bootstrap/Pagination';

/**
*
* props : 
* @Param length	
* @Param selectedPage	
* @Param onSelectPage	callback event when a page is selected
*
*/
export function GedeonPagination(props) {

	const [length] = useState(props.length);
	const [selectedPage, setSelectedPage] = useState(props.selectedPage === undefined ? 0 : props.selectedPage);
	
	function onSelectPage(page){
		setSelectedPage(page);
		if(props.onSelectPage !== undefined){
			props.onSelectPage(page);
		}
	}
	
	let nbPages = length === 0 ? 1 : Math.ceil(length/25);
	let elipStart = null;
	let elipEnd = null;
	
	let start = 1;
	let end = nbPages-1;
	if(selectedPage <= 5){
		start = 1;
		end = 8;
		//No elipisis if less than 11 pages
		if(nbPages > 11) {
			elipEnd = <Pagination.Ellipsis />;
		}
	} else if(selectedPage > 4 && selectedPage < nbPages-5){
		start = selectedPage-3;
		end = selectedPage+3;
		//No elipisis if less than 11 pages
		if(nbPages > 11) {
			elipStart = <Pagination.Ellipsis />;
			elipEnd = <Pagination.Ellipsis />;
		}
	} else {
		start = nbPages-8;
		end = nbPages-1;
		//No elipisis if less than 11 pages
		if(nbPages > 11) {
			elipStart = <Pagination.Ellipsis />;
		}
	}
	
	
	let pagination = [];
	for(let index = start; index < end; index++){
		pagination.push(<Pagination.Item active={index === selectedPage} onClick={(event) => onSelectPage(parseInt(event.target.text)-1)}>{index+1}</Pagination.Item>);
	}
	
	return(
		<Pagination centered>
			<Pagination.First onClick={() => onSelectPage(0)}/>
			<Pagination.Prev disabled={selectedPage === 0} onClick={() => onSelectPage(selectedPage-1)}/>
			<Pagination.Item active={0 === selectedPage} onClick={() => onSelectPage(0)}>{1}</Pagination.Item>
			{elipStart}
			{pagination}
			{elipEnd}
			<Pagination.Item active={nbPages-1 === selectedPage} onClick={() => onSelectPage(nbPages-1)}>{nbPages}</Pagination.Item>
			<Pagination.Next disabled={selectedPage === nbPages-1} onClick={() => onSelectPage(selectedPage+1)}/>
			<Pagination.Last onClick={() => onSelectPage(nbPages-1)}/>
		</Pagination>
	);
	
}