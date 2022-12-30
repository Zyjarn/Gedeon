import React, { useState } from 'react';

import { ContextualMenu } from './ContextualMenu';
import { GedeonActionsBar } from './GedeonActionsBar';
import { GedeonPagination } from './GedeonPagination';
import { GedeonSortable } from './GedeonSortable';

import Modal from '../modal/Modal';
import StringManager from '../../utils/StringManager';
import ResultSet from '../../api/ResultSet';
import TableSelector from '../tableselector/TableSelector';


import ContentSizeFilter from './filters/ContentSizeFilter';


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
import ICON_FOLDER from './mimetype/folder.svg';

import ICON_DELETE from './actions/delete.svg';
import ICON_PROPERTIES from './actions/properties.svg';
import ICON_REFRESH from './actions/refresh.svg';
import ICON_FILTER from './actions/filter.svg';
import ICON_SEND from './actions/send.svg';
import ICON_VIEW from './actions/view.svg';
import ICON_EXPORT from './actions/exportcsv.svg';
import ICON_COLUMNS from './actions/columns.svg';

import './GedeonContentList.css';

export function GedeonContentList(props) {

	const [modalSelectColumns, setModalSelectColumns] = useState(false);
	
	let resultSet = props.resultSet === undefined ? {columns:[], items:[], availableColumns:[]} : props.resultSet;
	let availableColumns = props.availableColumns === undefined ? [] : props.availableColumns;
	let selectedColumns = props.selectedColumns === undefined ? [] : props.selectedColumns;
	let [tmpAvailableColumns,setTmpAvailableColumns] = useState(availableColumns);
	let [tmpSelectedColumns,setTmpSelectedColumns] = useState(selectedColumns);
	let pageSize = props.pageSize === undefined ? 25 : props.pageSize;
	let selectedPage = props.selectedPage === undefined ? 0 : props.selectedPage;

	let sort = props.sort === undefined ? {columnDef:undefined, active:null} : props.sort;
	let filters = props.filters === undefined ? {} : props.filters;
	

	//Filters items
	let filteredItems = resultSet.items.map(x => x);
	for(let idx in filters){
		let filter = filters[idx];
		filteredItems = filteredItems.filter(obj => {
			if(filter.filter === "select"){
				return obj.attributes[idx] === filter.value;
			} else if(filter.filter === "string"){
				return obj.attributes[idx].toLocaleLowerCase().indexOf(filter.value.toLocaleLowerCase()) >= 0
			}
			return true;
		}).map((x) => x)
	}
	//Retrieve current page
	let startCurrent = selectedPage*pageSize;
	let endIndex = startCurrent+pageSize
	let endCurrent = filteredItems.length < endIndex ? filteredItems.length : endIndex;
	let currentPage = filteredItems.slice(startCurrent,endCurrent);
	

	/**
	* Sort items
	*/
	if(sort.active === 'up'){
		filteredItems.sort(
			(a, b) => (a.attributes[sort.columnDef.name] > b.attributes[sort.columnDef.name]) ? 
				1 : (a.attributes[sort.columnDef.name] === b.attributes[sort.columnDef.name]) ? 
				((a.attributes["id"] > b.attributes["id"]) ? 1 : -1) : -1 )
	} else if(sort.active === 'down'){
		filteredItems.sort(
			(a, b) => (a.attributes[sort.columnDef.name] < b.attributes[sort.columnDef.name]) ? 
				1 : (a.attributes[sort.columnDef.name] === b.attributes[sort.columnDef.name]) ? 
				((a.attributes["id"] < b.attributes["id"]) ? 1 : -1) : -1 );
	}
	
	

	
	
	/**
	* Sort event
	*/
	const handleUpdateSort = (newSort) => {
		let newProps = Object.assign({}, props);
		newProps.sort = newSort;
		props.onUpdateProps(newProps);
	}
	
	/**
	* ACTIONS LIST
	*/
	/**
	* On remove all filters
	*/
	const onRemoveFilters = () => {
		let newProps = Object.assign({}, props);
		newProps.filters = {};
		props.onUpdateProps(newProps);
	}
	
	const onOpenColumnSelector = () => {
		setModalSelectColumns(true);
	}

	/**
	* Filter column event
	*/
	const onFilterColumn = (columnDef, value) => {
		let newProps = Object.assign({}, props);
		if(newProps.filters === undefined){
			newProps.filters = {};
		}
		if(value === ""){
			delete newProps.filters[columnDef.name];
		} else {
			newProps.filters[columnDef.name] = {
				"value":value,
				"type":columnDef.type,
				"filter":columnDef.filter
			};
		}
		props.onUpdateProps(newProps);
	}

	
	/**
	* Event on right click on row to open contextMenu
	*/
	function onContextMenu(e){
		//Prevent opening of browser contextual menu
		e.preventDefault();
		
		let contextMenu = document.getElementsByClassName("contextualmenu_holder")[0];
		contextMenu.className=contextMenu.className+" active";
		
		let sizeCM = contextMenu.clientHeight;
		let ySub = 35;
		if((e.clientY+sizeCM) > window.innerHeight){
			ySub += sizeCM;
		}
		
		contextMenu.style.top = e.clientY-ySub +"px";
		contextMenu.style.left = e.clientX-35 +"px";
	}
	
	/**
	* Event when trigger checkbox to select all rows
	*/
	const onSelectAllLines = (value) => {
		let newProps = Object.assign({}, props);
		if(value){
			//if true, append current page to selection
			let start = pageSize*(selectedPage);
			let end = pageSize*(selectedPage+1);
			end = end > filteredItems.length ? filteredItems.length : end;
			//Array to contains id of items to update
			let ids = filteredItems.slice(start, end).map(x=>x.id);

			newProps.resultSet.items.forEach((obj) => {
				if(ids.indexOf(obj.id) >= 0){
					obj.selected = value;
				}
			});
		} else {
			// if false, deselect all selected items in resultSet
			newProps.resultSet.items.forEach((obj) => obj.selected = value);
		}
		props.onUpdateProps(newProps);
	}

	/**
	* Event when clicking on row or check selection input
	*/
	const onSelectLine = (event,idx, cancelSelection) => {
		event.stopPropagation();
		let newProps = Object.assign({}, props);
		let index = (pageSize*selectedPage)+parseInt(idx);
		let selectedItem = filteredItems[index];
		let value = event.target.checked !== undefined ? event.target.checked : !selectedItem.selected ;

		console.log("selectLine");
		if(cancelSelection === true){
			console.log(cancelSelection);
			newProps.resultSet.items.forEach(obj => obj.selected = false);
		}

		newProps.resultSet.items.forEach(obj => {
			if(obj.id === selectedItem.id){
				obj.selected = value;
			}
		});
		props.onUpdateProps(newProps);
	}
	
	/**
	* Event when resizing columns
	*/
	function onResizeColumn(event){
		event.preventDefault();
		let resizeEvt=event;
		
		//Retrieve column element to resize
		let parentElt = resizeEvt.target.offsetParent;
			if(parentElt.tagName.indexOf("SPAN") >= 0){
				parentElt = parentElt.offsetParent.offsetParent;
			}
		
		
		if(window.tmp === undefined){
			window.tmp = {};		
		}
		window.tmp["onmouseup"] = window.document.body.onmouseup;
		window.tmp["onmousemove"] = window.document.body.onmousemove;
		window.tmp["onmouseleave"] = window.document.body.onmouseleave;
		window.document.body.style.cursor = "col-resize !important";
		
		/**
		* On mouse up or mouse leave - stop resize
		*/
		let onStopResize = function(upEvent){
			window.document.body.onmouseup = window.tmp["onmouseup"];
			window.document.body.onmousemove = window.tmp["onmousemove"];
			window.document.body.onmouseleave = window.tmp["onmouseleave"];
			window.document.body.style.cursor = "default";
		}
		window.document.body.onmouseup = onStopResize;
		window.document.body.onmouseleave = onStopResize;
		
		/**
		* On mouse mouve - set new width to column
		**/
		window.document.body.onmousemove = function(moveEvent){
			
			let currentWidth = parentElt.style.width;
			let minWidth = parseInt(parentElt.style["min-width"].substr(0, parentElt.style["min-width"].length-2));
			currentWidth = parseInt(currentWidth.substr(0, currentWidth.length-2));
			//Get new width by compare screenX of previous event with screenX of move event
			let newWidth = (currentWidth-(resizeEvt.screenX-moveEvent.screenX));
			//Set width if not lower than min-width
			newWidth = newWidth < minWidth ? minWidth : newWidth;
			parentElt.style.width = newWidth+"px";
			//Set move event as new initial event
			resizeEvt=event=moveEvent;
		}
	}


	/**
	* Pagination event
	* setCurrentPage(filteredItems.slice(index,end));
	*/
	const handleSelectPage = (pageIndex) => {
		let newProps = Object.assign({},props);
		newProps.selectedPage = pageIndex;
		props.onUpdateProps(newProps);
	}
	
	const handleUpdatePageSize = (newPageSize) => {
		let newProps = Object.assign({},props);
		newProps.pageSize = newPageSize;
		props.onUpdateProps(newProps);
	}

	/**
	* Columns selection
	*/
	// On save column selection
	const handleValidateColumn = () => {
		let newProps = Object.assign({}, props);
		newProps.selectedColumns = tmpSelectedColumns;
		newProps.availableColumns = tmpAvailableColumns;
		setTmpSelectedColumns(tmpSelectedColumns);
		setTmpAvailableColumns(tmpAvailableColumns);
		setModalSelectColumns(false);
		props.onUpdateProps(newProps);
	}
	// On update column selection, not propagate till handle validate is not call
	const handleColumnUpdate = (selectorInfo) => {
		setTmpSelectedColumns(selectorInfo.selectedItems);
		setTmpAvailableColumns(selectorInfo.items);
	}
	
	const handleOnCloseColumns = () => {
		setTmpSelectedColumns(selectedColumns);
		setTmpAvailableColumns(availableColumns);
		setModalSelectColumns(false);
	}

	/**
	* Get Header Table row from columns definition in resultSet.columns
	*/
	function getHeader(){
		
		let cols = [];
		let sortable;
		//Add selection column
		let value = (currentPage.find((obj) => !(obj.selected === true)) === undefined) && currentPage.length > 0;
		if(props.resultSet !== undefined && props.resultSet.columns.length > 0){
			cols.push(
				<th key="select" className="headerCell select" style={{"width": "1.5rem"}}>
					<div className="columnHeader">
						<input type="checkbox" onChange={(e) => onSelectAllLines(e.target.checked)} checked={value}/>
					</div>
				</th>
			);
		}
		
		// Iterate through column definition defined in result set
		for(var i in resultSet.columns){
			let columnDef = resultSet.columns[i];
			
			sortable = <GedeonSortable 
							columnDef={columnDef} 
							onSort={handleUpdateSort} 
							active={sort.columnDef !== undefined && columnDef.name === sort.columnDef.name ? sort.active : null}/>;
			let label = columnDef.label;
			let style={"textAlign":"center"};
			let className = "";
			// get style
			if(columnDef.isStateIcon){
				className=className+" stateColumn";
				style["width"] = "32px";
				label = "";
			} else {
				let width = (StringManager.getTextWidth(columnDef.label,16)+80);
				style["minWidth"] = width+"px";
				style["width"] = width+"px";
			}
			
			if(columnDef.isHidden){
				style["display"] = "none";
			}
			
			let filter = <input size="1" className="space hidden" disabled/>;
			if(columnDef.filter === "string"){
				filter = <input size="1" 
								className="space" 
								value={filters[columnDef.name] === undefined ? "" : filters[columnDef.name].value }
								onChange={(e) => onFilterColumn(columnDef,e.target.value)}
						/>
			} else if(columnDef.filter === "select"){
				//Retrieve options from displayed rows
				let optsObj = resultSet.items.map((obj) => { 
							return {"value" : obj.attributes[columnDef.name], "label": obj.attributes[columnDef.name]}});
				optsObj = optsObj.filter((obj, index) => {
					return index === optsObj.findIndex(obj2 => {
						return obj2.value === obj.value;
					});
				}).map((obj,index) => (<option key={index} value={obj.value}>{obj.label}</option>));
				
				filter = (
					<select size="1" 
							className="space" 
							value={filters[columnDef.name] === undefined ? "" : filters[columnDef.name].value }
							onChange={(e) => onFilterColumn(columnDef,e.target.value)}>
						<option value=""></option>
						{optsObj}
					</select>
				);
			} else if(columnDef.filter === "long"){
				//
				filter = <ContentSizeFilter />;
			}
			
			
			//Add new column to list
			cols.push(
				<th key={i} style={style} className={className}>
					<div className="columnHeader flex">
						<div className="w column">
							<div className="flex">
								<div className="space"/>
								{label}
								{sortable}
								<div className="space"/>
								
							</div>
							<div className="filter flex">
								{filter}
							</div>
						</div>
						<div className="resizecolumn" onMouseDown={(e) => onResizeColumn(e)}>
							<span/>
						</div>
					</div>
				</th>
			);
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
		} else if("folder" === mimetype){
			return ICON_FOLDER;
		}
		return ICON_DEFAULT;
	}
	
	/**
	*
	*/
	function getTableBody() {
		
		
		let rows = [];
		// temp vars
		let columnDef;
		let item;
		let cells;
		
		for(let idx in currentPage){
			cells = [];
			
			item = currentPage[idx];
			
			//Add selection column
			cells.push(
				<td key={idx+"_select"} className="contentCell select" >
					<input 
						className="gedeoncontentlist-select" 
						type="checkbox" 
						checked={item.selected}
						onChange={(e) => onSelectLine(e,idx)}
					/>
				</td>
			);

			// Iterate throught selected columns
			for(let idxCol in resultSet.columns){
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
				cells.push(<td key={idx+"_"+idxCol} style={style} value={value} onClick={(e) => onSelectLine(e,idx,true)}>{display}</td>);
			}
			rows.push(<tr key={idx} onContextMenu={(e) => onContextMenu(e,idx)}>{cells}</tr>);
		}
		
		return (
			<tbody>
			{rows}
			</tbody>
		)
	}
	

		
	let actionsList = [
		{
			"type":"action",
			"name":"refresh",
			"label":"Refresh",
			"global":true,
			"multi":true,
			"icon": ICON_REFRESH
		},{
			"type":"action",
			"name":"remove filters",
			"label":"Remove filters",
			"global":true,
			"multi":true,
			"icon": ICON_FILTER,
			"action":onRemoveFilters
		},{
			"type":"action",
			"name":"view",
			"label":"View",
			"multi":true,
			"icon": ICON_VIEW,
			"sub": [
				{
					"type":"action",
					"name":"view2",
					"label":"View2",
					"multi":false
				}
			]
		},{
			"type":"action",
			"name":"properties",
			"label":"Properties",
			"multi":true,
			"icon": ICON_PROPERTIES
		},{
			"type":"action",
			"name":"sendemail",
			"label":"Send email",
			"multi":false,
			"icon": ICON_SEND,
			"sub": [
				{
					"type":"action",
					"name":"sendasoriginal",
					"label":"Send as Original",
					"multi":false,
					"sub": [
						{
							"type":"action",
							"name":"1",
							"label":"label 1",
							"multi":false
						},
						{
							"type":"action",
							"name":"2",
							"label":"label 2",
							"multi":false
						}
					]
				},
				{
					"type":"action",
					"name":"sendemailaspdf",
					"label":"Send as PDF",
					"multi":false
				},
				{
					"type":"action",
					"name":"sendemailaslink",
					"label":"Send as link",
					"multi":false,
					"sub": [
						{
							"type":"action",
							"name":"1",
							"label":"label 1",
							"multi":false
						},
						{
							"type":"action",
							"name":"2",
							"label":"label 2",
							"multi":false
						}
					]
				}
			]
		},{
			"type":"action",
			"name":"delete",
			"label":"Delete",
			"multi":true,
			"icon": ICON_DELETE
		},{
			"type":"splitter"
		},{
			"type":"global",
			"name":"exportcsv",
			"label":"Export as csv",
			"global":true,
			"multi":true,
			"icon": ICON_EXPORT
		},{
			"type":"global",
			"name":"columns",
			"label":"Select Columns",
			"multi":true,
			"icon": ICON_COLUMNS,
			"action": onOpenColumnSelector
		}
	];
	



	return (
		<div className="gedeon-contentlist hcolumn">
			<GedeonActionsBar actions={actionsList}/>

			<div className="tableholder">
				<div className="hscroll">
					<table>
						<thead>
						{getHeader()}
						</thead>
						{getTableBody()}
					</table>
				</div>
			</div>
			
			<div style={{ display: "flex", justifyContent: "center" }}>
				<GedeonPagination 
						items={filteredItems} 
						selectedPage={selectedPage} 
						pageSize={pageSize} 
						onSelectPage={handleSelectPage}
						onUpdatePageSize={handleUpdatePageSize}
				/>
			</div>
			<ContextualMenu actionsList={actionsList} />

			<Modal 
				isActive={modalSelectColumns} 
				title="Select columns" 
				onClose={handleOnCloseColumns}
				actions={
					[{label:"Save", onClick:handleValidateColumn}]
				}
				content={
					<TableSelector 
						items={tmpAvailableColumns} 
						selectedItems={tmpSelectedColumns}
						onUpdate={handleColumnUpdate}
					/>
				}
			/>
		</div>
	);
}
export default GedeonContentList