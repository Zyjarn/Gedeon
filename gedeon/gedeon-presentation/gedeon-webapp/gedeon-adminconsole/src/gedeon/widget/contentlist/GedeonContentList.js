import React, { Component } from 'react';

import Container from 'react-bootstrap/Container';
import Table from 'react-bootstrap/Table';
import Pagination from 'react-bootstrap/Pagination';

import { GedeonPagination } from './GedeonPagination';

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

import './GedeonContentList.css';

class GedeonContentList extends Component {

	/**
	* props must contains an instance of
	*/
	constructor(props) {
		super(props);
		let resultSet = props.resultSet;
		
		if(resultSet == undefined){
			resultSet = new ResultSet();
		}

		this.state = {
		  resultSet: resultSet,
		  selectedPage: 0,
		  pageSize: 25,
		  currentPage:resultSet.items.slice(0,25)
		};
	}

	componentDidMount() {

	}
	
	
	/**
	* Pagination event
	*/
	onSelectPage = (pageIndex) => {
		let index = pageIndex*25;
		let endIndex = index+25;
		let end = this.state.resultSet.items.length < endIndex ? this.state.resultSet.items.length : endIndex;
		this.setState({
			selectedPage: pageIndex,
			currentPage: this.state.resultSet.items.slice(index,end)
		});			
	}
	
	/**
	* render pagination module
	*/
	getPagination = () => {
		let nbPages = this.state.resultSet.items.length == 0 ? 1 : Math.ceil(this.state.resultSet.items.length/25);
		if(nbPages < 11){
			/**
			* When less than 11 pages no ellipsis
			*/
			return (
				<Pagination centered>
					<Pagination.First onClick={() => this.onSelectPage(0)}/>
					<Pagination.Prev disabled={this.state.selectedPage === 0} onClick={() => this.onSelectPage(this.state.selectedPage-1)}/>
					{Array.from({ length: nbPages }).map((_, index) => (
						<Pagination.Item active={index === this.state.selectedPage} onClick={(event) => this.onSelectPage(parseInt(event.target.text)-1)}>{index+1}</Pagination.Item>
						
					))}
					<Pagination.Next disabled={this.state.selectedPage === nbPages-1} onClick={() => this.onSelectPage(this.state.selectedPage+1)}/>
					<Pagination.Last onClick={() => this.onSelectPage(nbPages-1)}/>
				</Pagination>
			);
		} else {
			/**
			* If more than 10 pages add ellipsis depending of selected page
			*/
			let elipStart = null;
			let elipEnd = null;
			
			let start = 1;
			let end = nbPages-1;
			if(this.state.selectedPage <= 5){
				start = 1;
				end = 8;
				elipEnd = <Pagination.Ellipsis />;
			} else if(this.state.selectedPage > 4 && this.state.selectedPage < nbPages-5){
				start = this.state.selectedPage-3;
				end = this.state.selectedPage+3;
				elipStart = <Pagination.Ellipsis />;
				elipEnd = <Pagination.Ellipsis />;
			} else {
				start = nbPages-8;
				end = nbPages-1;
				elipStart = <Pagination.Ellipsis />;
			}
			
			
			let pagination = [];
			for(let index = start; index < end; index++){
				pagination.push(<Pagination.Item active={index === this.state.selectedPage} onClick={(event) => this.onSelectPage(parseInt(event.target.text)-1)}>{index+1}</Pagination.Item>);
			}
			
			return(
				<Pagination centered>
					<Pagination.First onClick={() => this.onSelectPage(0)}/>
					<Pagination.Prev disabled={this.state.selectedPage === 0} onClick={() => this.onSelectPage(this.state.selectedPage-1)}/>
					<Pagination.Item active={0 === this.state.selectedPage} onClick={() => this.onSelectPage(0)}>{1}</Pagination.Item>
					{elipStart}
					{pagination}
					{elipEnd}
					<Pagination.Item active={nbPages-1 === this.state.selectedPage} onClick={() => this.onSelectPage(nbPages-1)}>{nbPages}</Pagination.Item>
					<Pagination.Next disabled={this.state.selectedPage === nbPages-1} onClick={() => this.onSelectPage(this.state.selectedPage+1)}/>
					<Pagination.Last onClick={() => this.onSelectPage(nbPages-1)}/>
				</Pagination>
			);
		}
	}
	
	/**
	* Get Header Table row from columns definition in resultSet.columns
	*/
	getHeader = () => {
		
		let sortableNode = <div className='sortable'><div className='up' /><div className='down' /></div>;
		
		let cols = [];
		let columnDef = null;
		let sortable;
		let style;
		let label;
		// Iterate through column definition defined in result set
		for(var i in this.state.resultSet.columns){
			columnDef = this.state.resultSet.columns[i];
			sortable = columnDef.sortable == undefined || columnDef.sortable == true ? sortableNode : null;
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
	
	getStateIcon = (mimetype) => {
		if("application/pdf" == mimetype){
			return ICON_PDF;
		} else if(mimetype.startWith("image")){
			return ICON_IMG;
		} else if(mimetype.startWith("video")){
			return ICON_MEDIA;
		} else if(mimetype.startWith("audio")){
			return ICON_SOUND;
		} else if(mimetype === "message/rfc822" || mimetype === "application/vnd.ms-outlook"){
			return ICON_MSG;
		} else if (mimetype === "application/msword" || 
			mimetype.startWith("application/vnd.ms-works") ||
			mimetype === "application/vnd.openxmlformats-officedocument.wordprocessingml.document" ||
			mimetype.startWith("application/vnd.oasis.opendocument.text")){
			return ICON_DOC;
		} else if(mimetype === "application/vnd.oasis.opendocument.spreadsheet" ||
			mimetype === "application/vnd.ms-excel" ||
			mimetype.startWith("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") ){
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
		} else if(mimetype.startWith("application/vnd.oasis.opendocument.presentation") ||
			mimetype === "application/vnd.ms-powerpoint" ||
			mimetype === "application/vnd.openxmlformats-officedocument.presentationml.presentation"){
			return ICON_PPT;
		}
		return ICON_DEFAULT;
	}
	
	/**
	*
	*/
	getTableBody = () => {
		let rows = [];
		// temp vars
		let columnDef;
		let item;
		let cells;
		
		for(var idx in this.state.currentPage){
			cells = [];
			item = this.state.currentPage[idx];
			for(var idxCol in this.state.resultSet.columns){
				columnDef = this.state.resultSet.columns[idxCol];
			
				let value = item.attributes[columnDef.name];
				value = value == undefined ? null : value;
				let display = item.attributesDisplay[columnDef.name] == undefined ? value : item.attributesDisplay[columnDef.name];
				
				let style = {};
				
				if(columnDef.isHidden){
					style["display"] = "none";
				}
				if(columnDef.isStateIcon){
					display = (<img
									alt=""
									src={this.getStateIcon(value)}
									width="20"
									height="20"
									className="d-inline-block align-top"
								/>)
				}
				cells.push(<td key={idx+"_"+idxCol} style={style} value={value}>{display}</td>);
			}
			rows.push(<tr key={idx}>{cells}</tr>);
		}
		
		return (
			<tbody>
			{rows}
			</tbody>
		)
	}
	

	/**
	* Render component
	*/
	render() {
		
		let columnsDef = this.state.resultSet.columns.map((obj, index) => {
			return {
				"style":{
					"min-width":(StringManager.getTextWidth(obj,16)+80)+"px",
					"textAlign":"center"
				},
				"value": obj
			}
		});
		
		/*
		
		{columnsDef.map((obj, index) => (
						<th key={index} style={obj.style}><div className="columnHeader">{obj.value}<div className='sortable'><div className='up' /><div className='down' /></div></div></th>
					  ))}
		
		*/
		let pagination = GedeonPagination({
			length : this.state.resultSet.items.length,
			selectedPage : 0,
			onSelectPage: this.onSelectPage
		});
		

		return (
			<div>
				<Table responsive striped bordered hover>
					<thead>
					{this.getHeader()}
					</thead>
					{this.getTableBody()}
				</Table>
				<div style={{ display: "flex", justifyContent: "center" }}>
					{pagination}
				</div>
			</div>
		);
		//<Pagination.Ellipsis />
	}
}
export default GedeonContentList