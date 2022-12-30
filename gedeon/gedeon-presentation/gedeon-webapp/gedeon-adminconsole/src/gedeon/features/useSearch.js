import { useState } from 'react';

import GedeonContentList from '../widget/contentlist/GedeonContentList';
import Modal from '../widget/modal/Modal';
import SearchCriterias from '../widget/search/SearchCriterias';
import SearchCriteria from '../widget/search/SearchCriteria';

import ConfigAPI from '../api/ConfigAPI';
import ResultSet from '../api/ResultSet';

import ICON_BURGER from '../../images/burger.svg';
import ICON_SAVE from '../../images/save.svg';

import './Search.css';

export function useSearch(props) {
	
	const [modalTemplateActive,setModalTemplateActive] = useState(false);
	
	let allSearchTemplates = ConfigAPI.getAllSearchTemplates();
	let defaultTemplate = allSearchTemplates[0];


	let miniSearchPanel = props.mini === undefined ? false : props.mini;
	let searchTemplate = props.template === undefined ? defaultTemplate : props.template;
	let resultSet = props.resultSet === undefined ? {columns:searchTemplate.columns, items:[]} : props.resultSet;


	/**
	* On select Template
	*/
	const handleSelectTemplate = (templateName) => {
		let newProps = Object.assign({}, props);
		newProps.template = allSearchTemplates.find(obj => obj.name === templateName);
		props.onUpdateState(newProps);
	}

	/**
	* Handle change of template
	*/
	const handleTemplateChange = (template) => {
		let newProps = Object.assign({}, props);
		newProps.template = template;
		props.onUpdateState(newProps);
	}

	/**
	* Handle change on selected columns
	* - propagate change on template
	*/
	const handleUpdateColumns = (columnsInfo) => {
		let newProps = Object.assign({}, props);
		newProps.template.columns = columnsInfo.columns;
		newProps.template.availableColumns.columns = columnsInfo.availableColumns;
		props.onUpdateState(newProps);
	}

	/**
	* Launch Search
	*/
	const handleUpdateContentList = (contentListProps) => {
		let newProps = Object.assign({}, props);
		console.log("update");
		console.log(newProps);
		console.log(props);
		newProps.resultSet = contentListProps.resultSet;
		newProps.pageSize = contentListProps.pageSize;
		newProps.selectedPage = contentListProps.selectedPage;
		newProps.sort = contentListProps.sort;
		newProps.filters = contentListProps.filters;
		newProps.template.columns = contentListProps.selectedColumns;
		newProps.template.availableColumns.columns = contentListProps.availableColumns;
		props.onUpdateState(newProps);
	}

	/**
	* Launch Search
	*/
	const handleSearch = () => {
		let newResultSet = new ResultSet();
		newResultSet.columns = searchTemplate.columns;
		let newProps = Object.assign({}, props);
		newProps.resultSet = newResultSet;
		props.onUpdateState(newProps);
	}
	
	/**
	* Reduce search panel
	*/
	const onMiniSearchPanel = (value) => {
		let newProps = Object.assign({}, props);
		newProps.mini = value;
		props.onUpdateState(newProps);
	}

	return (
		<div className='h flex'>
			<div className="search-panel h flex">
				<SearchCriterias 
					mini={props.mini} 
					template={props.template} 
					allSearchTemplates={allSearchTemplates}
					onUpdateState={props.onUpdateState}
					onSelectTemplate={handleSelectTemplate}
					onTemplateChange={handleTemplateChange}
					onSearch={handleSearch}/>
				<div className="splitter" onClick={() => onMiniSearchPanel(!miniSearchPanel)}><span>&#60;</span></div>
			</div>
			
			<div className="search-result">
				<GedeonContentList 
					resultSet={resultSet} 
					availableColumns={searchTemplate.availableColumns} 
					selectedColumns={searchTemplate.columns}
					pageSize={props.pageSize}
					selectedPage={props.selectedPage}
					sort={props.sort}
					filters={props.filters}
					onUpdateColumns={handleUpdateColumns}
					onUpdateProps={handleUpdateContentList}
				/>
			</div>

		</div>
	);
	
}
export default useSearch;