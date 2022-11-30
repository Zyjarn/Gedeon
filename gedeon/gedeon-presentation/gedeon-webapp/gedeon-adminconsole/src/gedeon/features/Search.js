import React, { useState } from 'react';

import GedeonContentList from '../widget/contentlist/GedeonContentList';
import SearchCriteria from '../widget/search/SearchCriteria';

import ICON_BURGER from './burger.svg';

import './Search.css';

export function Search(props) {
	
	let defaultTemplate = {
		"searchCriterias":[
			{
				"label":"default",
				"criteria":"label",
				"value":""
			}
		],
		"availableCriterias" : [
		{
			"label":"Dog",
			"criteria":"dog",
			"value":""
		},
		{
			"label":"Cat",
			"criteria":"cat",
			"value":""
		},
		{
			"label":"Duck",
			"criteria":"duck",
			"value":""
		},
		{
			"label":"Parrot",
			"criteria":"parrot",
			"value":""
		},
		{
			"label":"Hamster",
			"criteria":"hamster",
			"value":""
		},
		{
			"label":"Goldfish",
			"criteria":"goldfish",
			"value":""
		}
	]
	};

	const [miniSearchPanel, setMiniSearchPanel] = useState(false);
	const [searchTemplate, setSearchTemplate] = useState(defaultTemplate);

	/**
	* Event triggered when a search criteria is selected to be added in search template
	* @param event	name of the selected criteria
	*/
	function addCriteria(event){
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
		
		setSearchTemplate({
			"searchCriterias":newCriterias,
			"availableCriterias": newAvailableCriterias
		});
	}
	
	/**
	* Callback from SearchCriteria when a criteria is removed
	* @param event	name of the selected criteria
	*/
	function removeCriteria(criteria){
		console.log("remove");
		console.log(criteria);
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
		
		setSearchTemplate({
			"searchCriterias":newCriterias,
			"availableCriterias": newAvailableCriterias
		});
	}
	
	
	function onChangeCriteriaValue(criteria, value){
		// Copy list of selected criterias
		let newCriterias = searchTemplate.searchCriterias.map(x => x);
		// Set the value of the criteria
		newCriterias[newCriterias.findIndex(obj => obj.criteria === criteria)].value=value;
		// Copy list of available criterias
		let newAvailableCriterias = searchTemplate.availableCriterias.map(x => x);
		
		setSearchTemplate({
			"searchCriterias":newCriterias,
			"availableCriterias": newAvailableCriterias
		});
	}
	
	
	
	
	let criterias = (
		<div className="searchCriteriasHolder">
			<div className="title">Search criterias</div>
			<div className="searchCriteriaList">
			{
				searchTemplate.searchCriterias.map((obj, index) => (
					<SearchCriteria key={index} criteria={obj} remove={removeCriteria} onChange={onChangeCriteriaValue}/>
				))
			}
			</div>
			<div className="searchCriteriaSplitter"/>
			<div className="searchCriteriaFooter">
				<div className="addcriteria">
					<select type="select" onChange={(event) => addCriteria(event)}>
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
				<button className="gedeonBtn">Search</button>
			</div>
		</div>
	);
	let searchCriteriasContent = criterias;
	if(miniSearchPanel){
		searchCriteriasContent = (
			<div>
				<div className="burger">
					<img
						alt="search criterias"
						src={ICON_BURGER}
						width="36"
						height="36" 
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
	let searchCriterias = <div className={"searchCriterias" + (miniSearchPanel ? " mini" : "")}>{searchCriteriasContent}</div>;
	
	return (
		<div className='gedeon-search'>
			<div className="search-panel">
				{searchCriterias}
				<div className="splitter" onClick={() => setMiniSearchPanel(!miniSearchPanel)}><span>&#60;</span></div>
			</div>
			
			<div className="search-result">
				<GedeonContentList />
			</div>
		</div>
	);
	
}
export default Search;