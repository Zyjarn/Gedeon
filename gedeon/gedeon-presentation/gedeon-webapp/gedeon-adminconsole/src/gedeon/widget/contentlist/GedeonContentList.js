import React, { Component } from 'react';

import Container from 'react-bootstrap/Container';
import Table from 'react-bootstrap/Table';
import Pagination from 'react-bootstrap/Pagination';

import StringManager from '../../utils/StringManager';
import ResultSet from '../../api/ResultSet';

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
		  selectedPage: 1,
		  pageSize: 25,
		  currentPage:resultSet.items.slice(0,25)
		};
	}

	componentDidMount() {

	}
	
	
	/**
	* Event when drag and drop is complete
	*/
	handleFileUpload = (e) => {
		this.prevent(e);
		this.dragEnd();
		//Send files to parent component
		this.callBackFileUpload(e.dataTransfer.files);
	}

	/**
	* Render component
	*/
	render() {
		
		let columnsDef = this.state.resultSet.columns.map((obj, index) => {
			console.log("min-width:"+(StringManager.getTextWidth(obj,16)+50)+"px");
			return {
				"style":{
					"min-width":(StringManager.getTextWidth(obj,16)+80)+"px",
					"textAlign":"center"
				},
				"value": obj
			}
		});

		return (
			<div>
				<Table responsive striped bordered hover>
				  <thead>
					<tr>
					  <th>#</th>
					  {columnsDef.map((obj, index) => (
						<th key={index} style={obj.style}><div className="columnHeader">{obj.value}<div className='sortable'><div className='up' /><div className='down' /></div></div></th>
					  ))}
					</tr>
				  </thead>
				  <tbody>
					{this.state.currentPage.map((item, indexRow) => (
						<tr key={indexRow}>
							{item.map((property, index) => (
								<td key={indexRow+"_"+index}>{index}</td>
							))}
							
						</tr>
					))}
				  </tbody>
				</Table>
				<div style={{ display: "flex", justifyContent: "center" }}>
					<Pagination centered>
						<Pagination.First />
						<Pagination.Prev />
						{Array.from({ length: Math.ceil(this.state.resultSet.items.length/25) }).map((_, index) => (
							<Pagination.Item>{index+1}</Pagination.Item>
						  ))}
						<Pagination.Next />
						<Pagination.Last />
					</Pagination>
				</div>
			</div>
		);
		//<Pagination.Ellipsis />
	}
}
export default GedeonContentList