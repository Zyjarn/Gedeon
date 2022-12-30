import React, { useState } from 'react';


export function ContextualMenu(props) {

	let actionsList = props.actionsList;

	const filterCM = (clazz, obj) => {
		return !(clazz === "filter" || clazz === "contextualmenu" || clazz === "sub" || clazz === "none" 
					|| clazz === "active" || (obj !== undefined && clazz === obj.name));
	}

	/**
	* Get an contextual item from element in the actionList tab
	*/
	const getAction = (obj, index, parentAction) => {
		if(obj.type === "splitter"){
			return (
				<div key={index} className={"cmsplitter"}>
				</div>
			);
		} else {
			let displaySubMenu = () => {};
			if(obj.sub !== undefined){
				displaySubMenu = () => {
					let subMenuToDisplay = document.getElementsByClassName("contextualmenu "+obj.name)[0];
					let parentClazz = [];
					if(subMenuToDisplay !== undefined ){
						subMenuToDisplay.classList.forEach(clazz => {
							if(filterCM(clazz,obj)){
								parentClazz.push(clazz);
							}
						});
					} 
					console.log(obj.name);
					
					let all = document.querySelectorAll(".contextualmenu.sub");
					all.forEach(subMenu => {
						console.log(parentClazz);
						let display = false;
						if(subMenu !== undefined ){
							console.log([... subMenu.classList]);
							display = [... subMenu.classList].filter(v => filterCM(v)).every(v => parentClazz.includes(v));
							console.log(display);
						}

						if(display){
							subMenu.classList.add('active');
							subMenu.classList.remove('none');
						} else {
							subMenu.classList.add('none');
							subMenu.classList.remove('active');
						}
					});
					if(subMenuToDisplay !== undefined){
						subMenuToDisplay.classList.add('active');
						subMenuToDisplay.classList.remove('none');
					}
				};
			}
			return (
				<div key={index} className={"action-item"+(obj.sub !== undefined ? " sub":"")}
					onMouseEnter={() => {displaySubMenu()}}
					onMouseLeave={() => {/*document.getElementsByClassName("contextualmenu "+obj.name)[0].className="contextualmenu "+obj.name+" none"*/}}
				>
					<span className="w">{obj.label}</span>
				</div>
			);
		}
	}

	
	let subMenus = [];
	const fillSubMenus = (actions,parentAction) => {
		let action;
		for(let i in actions){
			action = actions[i];
			if(action.sub !== undefined){

				//calculate position
				let style = {};
				let size = 0.3;
				for(let j = parseInt(i)+1; j < actions.length; j++){
					if(j.type === "splitter"){
						size += 0.3;
					} else {
						//+2 depend of padding
						size += 2;
					}
				}
				size = size - (action.sub.length)*2;
				style.bottom = Math.abs(size)+"rem";

				subMenus.push(
					<div key={action.name+i} className={"contextualmenu sub none "+action.name+" "+(parentAction === undefined ? "" : parentAction.name)} style={style}>
						{
							action.sub.map((obj, index) => getAction(obj,index,action))
						}
					</div>
				);
				action.sub.map((obj) => fillSubMenus([obj], action));
			}
		}
	}
	fillSubMenus(actionsList);

	return (
		<div className="contextualmenu_holder" 
					onMouseLeave={() => {/*document.getElementsByClassName("contextualmenu_holder")[0].className="contextualmenu_holder"*/}}
					onContextMenu={(e) => e.preventDefault()} >
			<div className="inlineblock">
				<div className="contextualmenu">
				{
					actionsList.map((obj, index) => getAction(obj,index))
				}
				</div>
				<div className="inlineblock">
					{subMenus}
				</div>
			</div>
		</div>
	);
	
}