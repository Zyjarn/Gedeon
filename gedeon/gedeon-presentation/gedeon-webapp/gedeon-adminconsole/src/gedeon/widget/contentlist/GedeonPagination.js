import React, { useState } from 'react';

/**
*
* props : 
* @Param length	
* @Param selectedPage	
* @Param onSelectPage	callback event when a page is selected
*
*/
export function GedeonPagination(props) {
	
	const ellipsis = <div className="pageItem ellipsis"><div>&#8943;</div></div>;

	const [length] = useState(props.length);
	const [pageSize] = useState(props.pageSize);
	const [selectedPage, setSelectedPage] = useState(props.selectedPage === undefined ? 0 : props.selectedPage);
	
	/*
	* event when page change
	*/
	function onSelectPage(page){
		setSelectedPage(page);
		if(props.onSelectPage !== undefined){
			props.onSelectPage(page);
		}
	}
	
	let nbPages = length === 0 ? 1 : Math.ceil(length/pageSize);
	let elipStart = null;
	let elipEnd = null;
	
	let start, end;
	if(nbPages < 11 ){
		start = 1;
		end = nbPages-1;
		//No elipisis if less than 11 pages
	} else if(selectedPage <= 5){
		start = 1;
		end = 8;
		elipEnd = ellipsis;	
	} else if(selectedPage > 4 && selectedPage < nbPages-5){
		start = selectedPage-3;
		end = selectedPage+3;
		elipStart = ellipsis;
		elipEnd = ellipsis;
		
	} else {
		start = nbPages-8;
		end = nbPages-1;
		elipStart = ellipsis;
	}
	
	
	let pagination = [];
	for(let index = start; index < end; index++){
		pagination.push(<div className={"pageItem"+(index === selectedPage ? " active":"")} key={index} onClick={(event) => {if(selectedPage !== index)onSelectPage(index)}}><div>{index+1}</div></div>);
	}
	
	return(
	<div>
		<div className="gedeonPagination">
			<div className="pageItem first" onClick={() => onSelectPage(0)}><div>&laquo;</div></div>
			<div className={"pageItem"+(selectedPage === 0 ? " disabled" : "")} onClick={() => {if(selectedPage !== 0)onSelectPage(selectedPage-1)}}><div>&lsaquo;</div></div>
			<div className={"pageItem"+(0 === selectedPage ? " active":"")} key={0} onClick={() => {if(selectedPage !== 0)onSelectPage(0)}}><div>{1}</div></div>
			{elipStart}
			{pagination}
			{elipEnd}
			{length === 0 ? null : <div className={"pageItem"+(nbPages-1 === selectedPage ? " active":"")} key={nbPages-1} onClick={() => {if(selectedPage !== nbPages-1)onSelectPage(nbPages-1)}}><div>{nbPages}</div></div>}
			<div className={"pageItem"+(selectedPage === nbPages-1 ? " disabled" : "")} onClick={() => {if(selectedPage !== nbPages-1)onSelectPage(selectedPage+1)}}><div>&rsaquo;</div></div>
			<div className="pageItem last" onClick={() => onSelectPage(nbPages-1)}><div>&raquo;</div></div>
		</div>		
	</div>
	);
	
}