import React, { useState } from 'react';

import { GedeonPagination } from './GedeonPagination';
import { GedeonSortable } from './GedeonSortable';

import StringManager from '../../utils/StringManager';
import ResultSet from '../../api/ResultSet';

import ICON_DEFAULT from './mimetype/default.svg';
import ICON_DOC from './mimetype/doc.svg';
import ICON_IMG from './mimetype/img.svg';
import ICON_MEDIA from './mimetype/media.svg';
import ICON_MSG from './mimetype/msg.svg';
import ICON_PDF from './mimetype/pdf.svg';
import ICON_PPT from './mimetype/ppt.svg';
import ICON_SOUND from './mimetype/sound.svg';
import ICON_XLS from './mimetype/xls.svg';
import ICON_ZIP from './mimetype/zip.svg';

import ICON_DELETE from './actions/delete.svg';
import ICON_PROPERTIES from './actions/properties.svg';
import ICON_REFRESH from './actions/refresh.svg';
import ICON_SEND from './actions/send.svg';
import ICON_VIEW from './actions/view.svg';
import ICON_EXPORT from './actions/exportcsv.svg';
import ICON_COLUMNS from './actions/columns.svg';

import './GedeonContentList.css';

export function GedeonContentList(props) {
	
	const [resultSet] = useState(props.resultSet === undefined ? new ResultSet() : props.resultSet);
	const [selectedPage, setSelectedPage] = useState(0);
	const [pageSize] = useState(25);
	const [sort, setSort] = useState({columnDef:null, active:null});
	const [filteredItems, setFilteredItems] = useState(resultSet.items);
	
	
	
	let actionsList = [
		{
			"name":"refresh",
			"icon": ICON_REFRESH
		},{
			"name":"view",
			"icon": ICON_VIEW
		},{
			"name":"properties",
			"icon": ICON_PROPERTIES
		},{
			"name":"sendemail",
			"icon": ICON_SEND
		},{
			"name":"delete",
			"icon": ICON_DELETE
		}
	];
	
	let tableActionsList = [
		{
			"name":"exportcsv",
			"icon": ICON_EXPORT
		},{
			"name":"columns",
			"icon": ICON_COLUMNS
		}
	]
	
	
	
	/**
	* Pagination event
	setCurrentPage(filteredItems.slice(index,end));
	*/
	function onSelectPage(pageIndex) {		
		setSelectedPage(pageIndex);			
	}
	
	/**
	* Sort event
	*/
	function onSort(state) {
		setSort(state);
		if(state.active === 'up'){
			setFilteredItems(filteredItems.sort(
				(a, b) => (a.attributes[state.columnDef.name] > b.attributes[state.columnDef.name]) ? 
					1 : (a.attributes[state.columnDef.name] === b.attributes[state.columnDef.name]) ? 
					((a.attributes["id"] > b.attributes["id"]) ? 1 : -1) : -1 ));
		} else if(state.active === 'down'){
			setFilteredItems(filteredItems.sort(
				(a, b) => (a.attributes[state.columnDef.name] < b.attributes[state.columnDef.name]) ? 
					1 : (a.attributes[state.columnDef.name] === b.attributes[state.columnDef.name]) ? 
					((a.attributes["id"] > b.attributes["id"]) ? 1 : -1) : -1 ));
		}
		
	}
	
	function onContextMenu(e){
		e.preventDefault();
		let contextMenu = document.getElementsByClassName("contextualmenu_holder")[0];
		contextMenu.className="contextualmenu_holder active";
		
		let sizeCM = contextMenu.clientHeight;
		let ySub = 35;
		if((e.clientY+sizeCM) > window.innerHeight){
			ySub += sizeCM;
		}
		
		contextMenu.style.top = e.clientY-ySub +"px";
		contextMenu.style.left = e.clientX-35 +"px";
	}
	
	function onSelectAllLines(select){
		console.log(select);
	}

	
	/**
	* Get Header Table row from columns definition in resultSet.columns
	*/
	function getHeader(){
		
		let cols = [];
		let columnDef = null;
		let sortable;
		let style;
		let label;
		//Add selection column
		cols.push(
			<th key="select" className="headerCell select" style={{"width": "1.5rem"}}>
				<div className="columnHeader">
					<input type="checkbox" onChange={(e) => onSelectAllLines(e.target.checked)}/>
				</div>
			</th>
		);
		
		// Iterate through column definition defined in result set
		for(var i in resultSet.columns){
			columnDef = resultSet.columns[i];
			
			sortable = (columnDef.sortable === undefined || columnDef.sortable === true) ? 
				<GedeonSortable columnDef={columnDef} onSort={onSort} active={columnDef === sort.columnDef ? sort.active : null}/> :
				null;
			label = columnDef.label;
			style={"textAlign":"center"};
			// get style
			if(columnDef.isStateIcon){
				style["width"] = "32px";
				label = "";
			} else {
				style["minWidth"] = (StringManager.getTextWidth(columnDef.label,16)+80)+"px";
			}
			
			if(columnDef.isHidden){
				style["display"] = "none";
			}
			
			//Add new column to list
			cols.push(<th key={i} style={style}><div className="columnHeader">{label}{sortable}</div></th>);
		}
		
		return (
			<tr>
			{cols}
			</tr>
		);
	}
	
	function getStateIcon(mimetype){
		if("application/pdf" === mimetype){
			return ICON_PDF;
		} else if(mimetype.startsWith("image")){
			return ICON_IMG;
		} else if(mimetype.startsWith("video")){
			return ICON_MEDIA;
		} else if(mimetype.startsWith("audio")){
			return ICON_SOUND;
		} else if(mimetype === "message/rfc822" || mimetype === "application/vnd.ms-outlook"){
			return ICON_MSG;
		} else if (mimetype === "application/msword" || 
			mimetype.startsWith("application/vnd.ms-works") ||
			mimetype === "application/vnd.openxmlformats-officedocument.wordprocessingml.document" ||
			mimetype.startsWith("application/vnd.oasis.opendocument.text")){
			return ICON_DOC;
		} else if(mimetype === "application/vnd.oasis.opendocument.spreadsheet" ||
			mimetype === "application/vnd.ms-excel" ||
			mimetype.startsWith("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") ){
			return ICON_XLS;
		} else if(mimetype === "application/zip" || 
			mimetype === "application/x-7z-compressed" || 
			mimetype === "application/x-tar" ||
			mimetype === "application/vnd.rar" || 
			mimetype === "application/java-archive" || 
			mimetype === "application/gzip" || 
			mimetype === "application/x-bzip" || 
			mimetype === "application/x-bzip2" || 
			mimetype === "application/x-freearc"){
			return ICON_ZIP;
		} else if(mimetype.startsWith("application/vnd.oasis.opendocument.presentation") ||
			mimetype === "application/vnd.ms-powerpoint" ||
			mimetype === "application/vnd.openxmlformats-officedocument.presentationml.presentation"){
			return ICON_PPT;
		}
		return ICON_DEFAULT;
	}
	
	/**
	*
	*/
	function getTableBody() {
			
		let index = selectedPage*pageSize;
		let endIndex = index+pageSize
		let end = filteredItems.length < endIndex ? filteredItems.length : endIndex;
		
		let currentPage = filteredItems.slice(index,end);
		
		let rows = [];
		// temp vars
		let columnDef;
		let item;
		let cells;
		
		for(var idx in currentPage){
			cells = [];
			cells.push(
				<td key={idx+"_select"} className="contentCell select" ><input className="gedeoncontentlist-select" type="checkbox" /></td>
			);
			item = currentPage[idx];
			for(var idxCol in resultSet.columns){
				columnDef = resultSet.columns[idxCol];
			
				let value = item.attributes[columnDef.name];
				value = value === undefined ? null : value;
				let display = item.attributesDisplay[columnDef.name] === undefined ? value : item.attributesDisplay[columnDef.name];
				
				let style = {};
				
				if(columnDef.isHidden){
					style["display"] = "none";
				}
				if(columnDef.isStateIcon){
					display = (<img
									alt=""
									src={getStateIcon(value)}
									width="20"
									height="20"
									className="d-inline-block align-top"
								/>)
				}
				cells.push(<td key={idx+"_"+idxCol} style={style} value={value}>{display}</td>);
			}
			rows.push(<tr key={idx} onContextMenu={(e) => onContextMenu(e)}>{cells}</tr>);
		}
		
		return (
			<tbody>
			{rows}
			</tbody>
		)
	}
	

	return (
		<div className="gedeon-contentlist">
			<div className="gedeon-actionslist">
				<div className="list">
					{
						actionsList.map((obj, index) => (
							<div key={index} className="action-item">
								<img
									alt={obj.name}
									src={obj.icon}
									width="24"
									height="24"
									className="d-inline-block align-top"
								/>
							</div>
						))
					}
				</div>
				<div className="space"/>
				<div className="list">
					{
						tableActionsList.map((obj, index) => (
							<div key={index} className="action-item">
								<img
									alt={obj.name}
									src={obj.icon}
									width="24"
									height="24"
									className="d-inline-block align-top"
								/>
							</div>
						))
					}
				</div>
			</div>
			<div className="tableholder">
				<table>
					<thead>
					{getHeader()}
					</thead>
					{getTableBody()}
				</table>
			</div>
			
			
			
			<div style={{ display: "flex", justifyContent: "center" }}>
				<GedeonPagination length={filteredItems.length} selectedPage={selectedPage} onSelectPage={onSelectPage} pageSize={pageSize} />
			</div>
			<div className="contextualmenu_holder" 
				onMouseLeave={() => document.getElementsByClassName("contextualmenu_holder")[0].className="contextualmenu_holder"}
				onContextMenu={(e) => e.preventDefault()}
			>
				<div className="contextualmenu">
				{
					actionsList.map((obj, index) => (
						<div key={index} className="action-item">
							{obj.name}
						</div>
					))
				}
				</div>
			</div>
		</div>
	);
}
export default GedeonContentList