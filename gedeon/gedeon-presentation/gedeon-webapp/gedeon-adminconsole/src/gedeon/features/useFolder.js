import React, { useState } from 'react';

import GedeonContentList from '../widget/contentlist/GedeonContentList';
import TreeView from '../widget/treeview/TreeView';
import Breadcrumb from '../widget/breadcrumb/Breadcrumb';

import './Folder.css';

export function useFolder(props) {

	let defaultTree = {
			"label": "Root",
			"value": "root",
			"root":true,
			"children": [
				{
					"label":"Child1",
					"value": "child1",
					"children": [
						{
							"label":"Child1.1",
							"value": "child1.1",
						},
						{
							"label":"Child1.2",
							"value": "child1.2",
							"children": [
								{
									"label":"Child1.2.1 zadzad zadza zad adz",
									"value": "child1.2.1",
								},
								{
									"label":"Child1.2.2 azdaz dz",
									"value": "child1.2.2",
								}
							]
						}
					]
				},
				{
					"label":"Child2",
					"value": "child2",
					"children": [
						{
							"label":"Child2.1",
							"value": "child2.1",
						}
					]
				},
				{
					"label":"Child3",
					"value": "child3"
				},
				{
					"label":"Child4",
					"value": "child4",
					"children": [
						{
							"label":"Child4.1",
							"value": "child4.1",
						},
						{
							"label":"Child4.2",
							"value": "child4.2",
							"children": [
								{
									"label":"Child4.2.1",
									"value": "child4.2.1",
								},
								{
									"label":"Child4.2.2",
									"value": "child4.2.2",
								},
								{
									"label":"Child4.2.3",
									"value": "child4.2.3",
								},
								{
									"label":"Child4.2.4",
									"value": "child4.2.4",
								},
								{
									"label":"Child4.2.5",
									"value": "child4.2.5",
								},
								{
									"label":"Child4.2.6",
									"value": "child4.2.6",
								},
								{
									"label":"Child4.2.7",
									"value": "child4.2.7",
								},
								{
									"label":"Child4.2.8",
									"value": "child4.2.8",
								},
								{
									"label":"Child4.2.9",
									"value": "child4.2.9",
								},
								{
									"label":"Child4.2.10",
									"value": "child4.2.10",
								},
								{
									"label":"Child4.2.11",
									"value": "child4.2.11",
								},
								{
									"label":"Child4.2.12",
									"value": "child4.2.12",
								}
							]
						}
					]
				},
				{
					"label":"Child5 dza azdd daz ddza",
					"value": "child5",
					"children": [
						{
							"label":"Child5.1",
							"value": "child5.1",
						}
					]
				},
				{
					"label":"Child6",
					"value": "child6"
				}

			]
	};

	const [columnDef] = useState(props.columnDef);
	let [tree, setTree]  = useState(defaultTree);
	
	/*
	* event when page change
	*/
	function onSort(up){
		if(props.onSort !== undefined){
			props.onSort({columnDef:columnDef,active:up});
		}
	}

	/**
	* Handle update of treeview
	*/
	const handleUpdateTree = (newTree) => {
		setTree(newTree);
	}
	
	return (
		<div className='feature-treeview h w flex'>
			<div className="h flex space">
				<div className="h flex">
					<div className="treeview-selector">
						<div className="selector-holder hcolumn">
							<div className="treeholder yover">
								<TreeView 
									tree={tree}
									onUpdateTree={handleUpdateTree}
								/>
							</div>
							<div className="space"/>
						</div>
					</div>
					<div className="splitter" ><span>&#60;</span></div>
				</div>
				<div className="treeview-result space">
					<div className="column">
						<Breadcrumb tree={tree}/>
						<GedeonContentList />
					</div>
				</div>
			</div>
		</div>
	);
	
}
export default useFolder;
