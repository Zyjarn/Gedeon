import React, { useState } from 'react';

import ICON_BM from './actions/burger-menu.svg';

export function GedeonActionsBar(props) {
	
	let actions = props.actions === undefined ? [] : props.actions;

	
	const getActionItem = (obj, index) => {
		return (
			<div key={index} className="action-item activeIcon" onClick={() => {if(obj.action !== undefined)obj.action()} }>
				<img
					alt={obj.name}
					title={obj.label}
					src={obj.icon}
				/>
			</div>);
	};

	const getMiniAction = (obj, index) => {
		if (obj.type === "splitter") {
			return (<div key={index} className = "context-splitter"/>);
		} else {
			return (
				<div key={index} className="action-item">
					<img
						alt={obj.name}
						title={obj.label}
						src={obj.icon}
					/>
					<span>{obj.label}</span>
				</div>);
		}
	};

	let miniActions = [];
	let actionsNodes = [];
	let tempActions = [];
	for(let i in actions){
		let action = actions[i];
		if(action.type === "action"){
			miniActions.push(getMiniAction(action,i));
			tempActions.push(action);
		} else {
			if(tempActions.length > 0 ){
				actionsNodes.push(
					<div key={i} className="flex maxi">
						{
							tempActions.map((obj, index) => (
								getActionItem(obj,index)
							))
						}
					</div>
				);
				tempActions = [];
			}
			if (action.type === "splitter") {
				miniActions.push(getMiniAction(action,i));
				actionsNodes.push(<div key={i+"s"} className="space"/>);
			} else if(action.type === "global") {
				actionsNodes.push(
					<div key={i} className="flex">
						{getActionItem(action)}
					</div>
				);
			}
			
			
		}
	}


	return (
		<div className="actionsBar flex">
			<div className="flex mini">
				<div className="action-item activeIcon">
					<img
						alt="Actions"
						title="Actions"
						src={ICON_BM}
					/>
				</div>
				<div className="contextualmenu">
				{miniActions}
				</div>
			</div>
			{actionsNodes}
		</div>	
	);
	
}

export default GedeonActionsBar;