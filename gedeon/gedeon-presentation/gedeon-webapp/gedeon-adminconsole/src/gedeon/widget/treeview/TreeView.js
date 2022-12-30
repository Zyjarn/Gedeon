import { useState } from 'react';

import './TreeView.css'

export function TableSelector(props) {

	let tree = props.tree === undefined ? {} : props.tree;
	let closeIcon = props.closeIcon === undefined ? {} : props.closeIcon;
	let openIcon = props.openIcon === undefined ? {} : props.openIcon;


	/**
	* Search a node in a tree by value
	*/
	function searchNode(node, value){
		if(node.value === value){
			return node;
		} else if (node.children !== undefined){
			var result = null;
			for(let i=0; result === null && i < node.children.length; i++){
			   result = searchNode(node.children[i], value);					
			}
			return result;
		}
		 return null;
	}

	/**
	* Search a node in a tree by value
	*/
	function updateNodes(node, callback,value, callbackMatch){
		if(node.value === value){
			callbackMatch(node);
		} else {
			callback(node);
		}
		if (node.children !== undefined){
			for(let i=0; i < node.children.length; i++){
			   updateNodes(node.children[i], callback,value, callbackMatch);					
			}
		}
	}


	/**
	* Event extends node
	*/
	const onClickDropDown = (event,value) => {
		event.stopPropagation();
		let newTree = Object.assign({}, props.tree);
		let node = searchNode(newTree,value);
		node.active = node.active === undefined ? true : !node.active;
		props.onUpdateTree(newTree);
	}

	/**
	* Event on select node
	*/
	const onSelectNode = (value) => {
		console.log(value);
		let newTree = Object.assign({}, props.tree);
		updateNodes(newTree,(node) => {node.selected=false},value,(node) => {console.log(node);node.selected=true;});
		props.onUpdateTree(newTree);
	}


	function getNodes(node){
		let content = null;
		if(node.children === undefined){
			return (
				<li key={node.value}>
					<div className="tree-item flex"
							onClick={() => onSelectNode(node.value)}>
						<span className={(node.selected === true ? " selected" : "")} >
							{node.label}
						</span>
					</div>
				</li>);
		} else {
			let className = " none"
			let className2 = ""
			if(node.active){
				className = " active";
				className2 = " active";
			}
			content = (
				<li key={node.value}>
					<div className="tree-item flex"
							onClick={() => onSelectNode(node.value)}>
						<div className={"dropdown"+className2} onClick={(e) => onClickDropDown(e,node.value)}/>
						<span className={(node.selected === true ? " selected" : "")} >{node.label}</span>
					</div>
					<ul className={"childs"+className}>
						{node.children.map((obj) => getNodes(obj))}
					</ul>
				</li>
			);
		}
			
		return content;
	}

	let className = " none"
let className2 = "";
let className3 = "";
	if(tree.active){
		className = " active";
className2 = " active";
	}
	let treeviewNodes = [];
	//for(var i in tree){
		treeviewNodes.push(
			<div className="treeview_holder w column">
				<div className="root tree-item flex"
						onClick={() => onSelectNode(tree.value)}>
					<div className={"dropdown"+className2} onClick={(e) => onClickDropDown(e,tree.value)}/>
					<span className={(tree.selected === true ? " selected" : " ")}>{tree.label}</span>
				</div>
				<ul className={className}>
					{tree.children === undefined ? null : tree.children.map((obj) => getNodes(obj))}
				</ul>
			</div>		
		);
	//}


	return (
		<div className="treeview flex">
			
				{treeviewNodes}	
		</div>
	);
	
}
export default TableSelector;