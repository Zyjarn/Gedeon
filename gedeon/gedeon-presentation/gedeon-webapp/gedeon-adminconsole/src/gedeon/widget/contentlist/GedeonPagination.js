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

	let length = props.items.length;
	let nbSelected = props.items.map(x => x).filter(obj => obj.selected === true).length;
	let pageSize = props.pageSize === undefined ? 25 : props.pageSize;
	let nbPages = length === 0 ? 1 : Math.ceil(length/pageSize);
	let selectedPage = props.selectedPage === undefined ? 0 : props.selectedPage > nbPages ? 0 : props.selectedPage;
	
	/*
	* event when page change
	*/
	function onSelectPage(page){
		//setSelectedPage(page);
		if(props.onSelectPage !== undefined){
			props.onSelectPage(page);
		}
	}
	
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
	
	let miniPagination = (
		<select onChange={(event) => {if(selectedPage !== parseInt(event.target.value))onSelectPage(parseInt(event.target.value))}} value={selectedPage}>
			{Array.from({length:nbPages}).map((obj,index) => (<option key={index} value={index}>{index+1}</option>))}
		</select>);

	let selection = (nbSelected > 0) ? (
				<div className="flex">
					<span className="marginright">Selected : {nbSelected}</span>
				</div>) : (<div className="flex hidden">
					<span className="marginright">Select : {length}</span>
				</div>);
	
	return(
	<div className="gedeonPagination w flex center">
		<div className="flex">
			<span className="marginright">Total : {length}</span>
		</div>
		{selection}
		<div className="space"/>		
		<div className="gedeonPagination column">
			<div className="maxi flex">
				<div className="pageItem first" onClick={() => onSelectPage(0)}><div>&laquo;</div></div>
				<div className={"pageItem"+(selectedPage === 0 ? " disabled" : "")} onClick={() => {if(selectedPage !== 0)onSelectPage(selectedPage-1)}}><div>&lsaquo;</div></div>
				<div className={"pageItem"+(0 === selectedPage ? " active":"")} key={0} onClick={() => {if(selectedPage !== 0)onSelectPage(0)}}><div>{1}</div></div>
				{elipStart}
				{pagination}
				{elipEnd}
				{length === 0 || length < pageSize ? null : <div className={"pageItem"+(nbPages-1 === selectedPage ? " active":"")} key={nbPages-1} onClick={() => {if(selectedPage !== nbPages-1)onSelectPage(nbPages-1)}}><div>{nbPages}</div></div>}
				<div className={"pageItem"+(selectedPage === nbPages-1 ? " disabled" : "")} onClick={() => {if(selectedPage !== nbPages-1)onSelectPage(selectedPage+1)}}><div>&rsaquo;</div></div>
				<div className="pageItem last" onClick={() => onSelectPage(nbPages-1)}><div>&raquo;</div></div>
			</div>
			<div className="mini flex">
				<div className="pageItem first" onClick={() => onSelectPage(0)}><div>&laquo;</div></div>
				<div className={"pageItem"+(selectedPage === 0 ? " disabled" : "")} onClick={() => {if(selectedPage !== 0)onSelectPage(selectedPage-1)}}><div>&lsaquo;</div></div>
				{miniPagination}
				<div className={"pageItem"+(selectedPage === nbPages-1 ? " disabled" : "")} onClick={() => {if(selectedPage !== nbPages-1)onSelectPage(selectedPage+1)}}><div>&rsaquo;</div></div>
				<div className="pageItem last" onClick={() => onSelectPage(nbPages-1)}><div>&raquo;</div></div>
			</div>	
		</div>
		<div className="space"/>
		<div className="flex">
			<span className="marginright">Page size </span>
			<select value={props.pageSize} onChange={(e) => props.onUpdatePageSize(e.target.value)}>
				<option value={10}>10</option>
				<option value={25}>25</option>
				<option value={50}>50</option>
				<option value={100}>100</option>
				<option value={200}>200</option>
				<option value={500}>500</option>
			</select>
		</div>
	</div>
	);
	
}