import { useState } from 'react';

import Modal from '../modal/Modal';
import SearchCriteria from '../search/SearchCriteria';

import ConfigAPI from '../../api/ConfigAPI';
import ResultSet from '../../api/ResultSet';

import ICON_BURGER from '../../../images/burger.svg';
import ICON_SAVE from '../../../images/save.svg';

export function SearchCriterias(props) {
	
	const [modalTemplateActive,setModalTemplateActive] = useState(false);
	
	let allSearchTemplates = props.allSearchTemplates;
	let defaultTemplate = allSearchTemplates[0];
	let searchTemplate = props.template === undefined ? defaultTemplate : props.template;

	/**
	* Event triggered when a search criteria is selected to be added in search template
	* @param event	name of the selected criteria
	*/
	function handleAddCriteria(event){
		if(event.target.value === ""){
			return;
		}
		let criteria = event.target.value;
		event.target.value = "";
		
		// Copy list of available criterias
		let newAvailableCriterias = searchTemplate.availableCriterias.map(x => x);
		// Remove the criteria from the copied list
		let newCriteria = newAvailableCriterias.splice(newAvailableCriterias.findIndex(obj => obj.criteria === criteria),1)[0];
		// Copy list of selected criterias
		let newCriterias = searchTemplate.searchCriterias.map(x => x);
		// Add removed criteria to the copied list
		newCriterias.push(newCriteria);
		
		let newTemplate = Object.assign({}, searchTemplate);
		newTemplate.searchCriterias = newCriterias;
		newTemplate.availableCriterias = newAvailableCriterias;

		props.onTemplateChange(newTemplate);
	}
	
	/**
	* Callback from SearchCriteria when a criteria is removed
	* @param event	name of the selected criteria
	*/
	const handleRemoveCriteria = (criteria) => {
		if(criteria === ""){
			return;
		}
		
		// Copy list of selected criterias
		let newCriterias = searchTemplate.searchCriterias.map(x => x);
		// Remove the criteria from the copied list
		let newCriteria = newCriterias.splice(newCriterias.findIndex(obj => obj.criteria === criteria),1)[0];
		//reset value on criteria
		newCriteria.value = "";
		// Copy list of available criterias
		let newAvailableCriterias = searchTemplate.availableCriterias.map(x => x);
		// Add removed criteria to the copied list
		newAvailableCriterias.push(newCriteria);
		
		let newTemplate = Object.assign({}, searchTemplate);
		newTemplate.searchCriterias = newCriterias;
		newTemplate.availableCriterias = newAvailableCriterias;

		props.onTemplateChange(newTemplate);
	}
	
	
	const onChangeCriteriaValue = (criteria, value) => {
		let newTemplate = Object.assign({}, searchTemplate);
		// Set the value of the criteria
		newTemplate.searchCriterias.find(obj => obj.criteria === criteria.criteria).value=value;
		props.onTemplateChange(newTemplate);
	}
	

	const handleCloseSaveTemplate = () => {
		setModalTemplateActive(false);
	}

	const handleOpenSaveTemplate = () => {
		setModalTemplateActive(true);
	}

	/*
	* Build criterias list
	*/
	let criterias = (
		<div className="searchCriteriasHolder h column">
			<div className="title textSizeL bold">Search Template</div>
			<div className="searchTemplateSelector yover flex">
				<select type="select" value={searchTemplate.name} onChange={(e) => props.onSelectTemplate(e.target.value)}>
					{
						allSearchTemplates.map((obj,index) => (
							<option key={index} value={obj.name}>{obj.label}</option>
						))
					}
				</select>
				<div className="action-item activeIcon" onClick={() => handleOpenSaveTemplate()}>
					<img
						alt="Save Search Template"
						title="Save Search Template"
						src={ICON_SAVE}
					/>
				</div>
			</div>
			<div className="title textSizeL bold">Search criterias</div>
			<div className="searchCriteriaList yover">
			{
				searchTemplate.searchCriterias.map((obj, index) => (
					<SearchCriteria key={index} criteria={obj} remove={handleRemoveCriteria} onChange={onChangeCriteriaValue}/>
				))
			}
			</div>
			<div className="searchCriteriaSplitter"/>
			<div className="searchCriteriaFooter">
				<div className="addcriteria">
					<select type="select" onChange={(event) => handleAddCriteria(event)}>
						<option value=""></option>
						{
							searchTemplate.availableCriterias.map((obj,index) => (
								<option key={index} value={obj.criteria}>{obj.label}</option>
							))
						}
					</select>
					<span />
					<button className="gedeonBtn">Add</button>
				</div>
				<button className="gedeonBtn" onClick={() => props.onSearch()}>Search</button>
			</div>
		</div>
	);

	/**
	* Mini Seach Content
	*/
	let searchCriteriasContent = criterias;
	if( props.mini){
		searchCriteriasContent = (
			<div>
				<div className="action-item activeIcon burger">
					<img
						alt="Search Criterias"
						title="Search Criterias"
						src={ICON_BURGER}
					/>
				</div>
				<div className="searchCriterias-modal">
					<div className="criteriasmodal">
						<div className="criteria-content" >
							{searchCriteriasContent}
						</div>
					</div>
				</div>
			</div>
			
		);
	}
	
	return (
		<div className={"searchCriterias" + ( props.mini ? " minipanel" : "")}>
			{searchCriteriasContent}
			<Modal isActive={modalTemplateActive} title="Save Search Template" content="Content" onClose={handleCloseSaveTemplate}/>
		</div>
	);
	
}
export default SearchCriterias;