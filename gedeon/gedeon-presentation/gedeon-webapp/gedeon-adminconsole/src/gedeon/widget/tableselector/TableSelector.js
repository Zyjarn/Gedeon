import { useState } from 'react';

import './TableSelector.css'

export function TableSelector(props) {

	let items = props.items === undefined ? [] : props.items;
	items.sort((obj1,obj2) => obj1.label.localeCompare(obj2.label));
	let selectedItems = props.selectedItems === undefined ? [] : props.selectedItems;
	
	/**
	* Move item from left table to right table
	*/
	const handleAddSelected = (e) => {
		let newSelectItems = selectedItems.map(x => x);
		let newItems = items;
		let left = e.target.parentNode.parentNode.parentNode.children[0].children[1];
		for(let i in left.options){
			if(left.options[i].selected){
				let item = items[i];
				newItems = items.filter(obj => obj.name !== item.name).map(x => x);
				newSelectItems.push(item);
			}
		}
		props.onUpdate({items:newItems, selectedItems:newSelectItems});
	}

	/**
	* Move item from right table to left table
	*/
	const handleRemoveSelected = (e) => {
		let newSelectedItems = selectedItems;
		let newItems = items.map(x => x);
		let right = e.target.parentNode.parentNode.parentNode.children[2].children[1];
		for(let i in right.options){
			if(right.options[i].selected){
				let item = newSelectedItems[i];
				newSelectedItems = selectedItems.filter(obj => obj.name !== item.name).map(x => x);
				newItems.push(item);
			}
		}
		props.onUpdate({items:newItems, selectedItems:newSelectedItems});
	}

	/**
	* Move Up or Down the selected items in the right table
	*/
	const handleUpOrDownSelectedItems = (e,up) => {
		// retrieve input from target
		let right = e.target.parentNode.parentNode.parentNode.children[2].children[1];
		
		// build table with selected and not selected items
		let rightItemsSelected = [];
		let rightItemsNotSelected = [];
		let startPos = -1;
		// by iterate on options of input
		for(let i in [...right.options]){
			let item = props.selectedItems[i];
			
			if(right.options[i].selected){
				if(startPos === -1){
					startPos = parseInt(i);
				}
				item.selected=true;
				rightItemsSelected.push(item);
			} else if(right.options[i].value !== undefined){
				item.selected=false;
				rightItemsNotSelected.push(item);
			}
		}
		// no item selected, stop event
		if(rightItemsSelected.length === 0)
			return;

		// build new table of selected items
		let newSelectedItems = rightItemsNotSelected.slice(0,startPos-1);
		newSelectedItems = newSelectedItems.concat(rightItemsSelected);
		newSelectedItems = newSelectedItems.concat(rightItemsNotSelected.slice(startPos-1));
		
		// set selected attribute on input options with new array
		// encounter some issue by just setting the selected attribute in render function
		for(let i in [...right.options]){
			right.options[i].selected = newSelectedItems[i].selected;
		}

		// propagate to parent
		props.onUpdate({items:props.items, selectedItems:newSelectedItems});
	}

	/**
	* Up position of selected items on right table
	*/
	const handleUpSelected = (e) => {
		handleUpOrDownSelectedItems(e,true);
	}

	/**
	* Down position of selected items on right table
	*/
	const handleDownSelected = (e) => {
		handleUpOrDownSelectedItems(e,false);
	}

	/**
	* RENDER
	*/
	return (
		<div className="tableselector flex" onClick={(e) => {e.stopPropagation();}}>
			<div className="column">
				<div className="bold">Available</div>
				<select size="7" className="bordered" multiple>
				{
					items.map((obj,index) => (
						<option key={index} value={obj.name} selected={obj.selected}>{obj.label}</option>
					))
				}
				</select>
			</div>
			<div className="flex center">
				<div className="column">
					<div className="space"/>
					<button onClick={(e) => handleAddSelected(e)}>&gt;</button>
					<button onClick={(e) => handleRemoveSelected(e)}>&lt;</button>
					<div className="space"/>
				</div>
			</div>
			<div className="column">
				<div className="bold">Selected</div>
				<select size="7" className="bordered" multiple>
				{
					selectedItems.map((obj,index) => (
						<option key={index} value={obj.name} selected={obj.selected}>{obj.label}</option>
					))
				}
				</select>
			</div>
			<div className="flex center">
				<div className="column">
					<div className="space"/>
					<button onClick={(e) => handleUpSelected(e)}>&#x25B3;</button>
					<button onClick={(e) => handleDownSelected(e)}>&#x25BD;</button>
					<div className="space"/>
				</div>
			</div>
		</div>
	);
	
}
export default TableSelector;