import {useState} from 'react';

import './App.css';
import GedeonHeader from "./gedeon/layout/GedeonHeader";
import Footer from "./gedeon/layout/Footer";

import Favorites from './gedeon/features/Favorites';
import useSearch from './gedeon/features/useSearch';
import useFeature from './gedeon/features/useFeature';

import GedeonContentList from './gedeon/widget/contentlist/GedeonContentList';
import DocumentProperties from './gedeon/widget/docproperties/DocProperties.js';

import ConfigAPI from './gedeon/api/ConfigAPI';
import CacheTabManager from './gedeon/utils/CacheTabManager';


import ICON_FAVORITES from './images/favorites.svg';
import ICON_FOLDER from './images/folder.svg';
import ICON_SEARCH from './images/search.svg';
import ICON_TREEVIEW from './images/treeview.svg';
import ICON_SETTINGS from './images/settings.svg';

import ICON_CLOSE from './images/close.svg';

function App() {
	
	
	//TODO To externalize
	//List of configured app view
	let config =
		{
			appViews : [
				{
					"name":"favorites",
					"icon":ICON_FAVORITES
				},
				{
					"name":"folder",
					"icon":ICON_FOLDER
				},
				{
					"name":"search",
					"icon":ICON_SEARCH
				},
				{
					"name":"treeview",
					"icon":ICON_TREEVIEW
				}			
			],
			
			appViewsConfig : {
				"favorites":{
					"name":"favorites",
					"cache":"favorites",
					"icon":ICON_FAVORITES,
					"isActive":true,
					"isOpenNewTab":false,
					"isClosable":false
				},
				"folder":{
					"name":"folder",
					"icon":ICON_FOLDER,
					"isOpenNewTab":false,
					"isClosable":false
				},
				"search":{
					"name":"search",
					"icon":ICON_SEARCH,
					"isOpenNewTab":true
				},
				"treeview":{
					"name":"treeview",
					"icon":ICON_TREEVIEW,
					"isOpenNewTab":true
				},
				"settings" : {
					"name":"settings",
					"icon":ICON_SETTINGS,
					"isOpenNewTab":false
				}
			},
		
			adminAppViews : [
				{
					"name":"settings",
					"icon":ICON_SETTINGS
				}
			]
		};

	const [cacheTab, setCacheTab] = useState(new CacheTabManager({cache:{"favorites":config.appViewsConfig["favorites"]}}));
	const [reducedElements, setReducedElements] = useState([]);
	const [propertiesElement, setPropertiesElement] = useState();
	
	/**
	* Handle click on feature in navigation pane
	*/
	const addTab = (appView) => {
		let tabconfig = config.appViewsConfig[appView];
		let add = true;
		let count = 0;
		let sameTypeTabs = [];

		tabconfig.label = tabconfig.name;
		

		let newCacheTab = new CacheTabManager(cacheTab);
		let allTabs = newCacheTab.get()
		for(var i in allTabs){
			//Disaable active on all tabs
			
			if(allTabs[i].name === tabconfig.name){
				//check if tab must be added or re-open
				add = tabconfig.isOpenNewTab;
				allTabs[i].isActive = !add;				
				sameTypeTabs.push(allTabs[i]);
			} else {
				allTabs[i].isActive = false;
			}
		}


		if(add){
			tabconfig.id = 0;
			tabconfig.isActive = true;
			if(sameTypeTabs.length > 0){
				tabconfig.id = Math.max(...sameTypeTabs.map(obj => obj.id))+1;
				tabconfig.label += " ("+tabconfig.id+")";
			}
			tabconfig.cache = tabconfig.name+tabconfig.id;
			
			//Update state			
			newCacheTab.update(tabconfig.cache,tabconfig);
			
		} 
		setCacheTab(newCacheTab);
	}
	
	/**
	* Event OnClick on Tab Feature
	*/
	const onSelectTab = (obj) => {
		let newCacheTab = new CacheTabManager(cacheTab);
		newCacheTab.get().forEach(obj => obj.isActive = false);
		newCacheTab.get(obj.cache).isActive = true;
		setCacheTab(newCacheTab);		
	}
	
	/**
	* Event onClose Feature Tab
	*/
	const onCloseTab = (obj) => {
		let newCacheTab = new CacheTabManager(cacheTab);
		// remove selected tab
		newCacheTab.remove(obj.cache);
		// if delete active tab, select a new one
		if(obj.isActive){
			Object.values(newCacheTab.cache)[0].isActive = true;
		}
		setCacheTab(newCacheTab);
	}


	const handleUpdateFeatureProps = (updatedProps) => {
		let newCacheTab = new CacheTabManager(cacheTab);
		newCacheTab.update(updatedProps.cache,updatedProps);
		setCacheTab(newCacheTab);
	}


	


	
	
	/**
	* Get an image node for the navigation bar
	*
	* @param appViewDef	AppViewDef( @param name	feature name, @param icon	imported svg or target image file)
	*/
	function getAppViewLink(appViewDef){
		/*if(appViewDef.name === selectedItem){
			className += " active";
		}*/
		
		return ( 
			<span key={appViewDef.name} className={"nav-link"} onClick={() => addTab(appViewDef.name)}>
				<img
					alt={appViewDef.name}
					title={appViewDef.name}
					src={appViewDef.icon}
				/>
			</span>
		);
	}

	
		
	let appViewLinks = [];
	config.appViews.map((obj, index) => (
		appViewLinks.push(getAppViewLink(obj))
	));
	
	let adminAppViewLinks = [];
	config.adminAppViews.map((obj, index) => (
		adminAppViewLinks.push(getAppViewLink(obj))
	));


	let selectedFeature = null;
	let tabsProps = {};
	let tabsConfigs = cacheTab.get();
	
	for(var i in tabsConfigs){
		if(tabsConfigs[i].isActive){
			selectedFeature = tabsConfigs[i];
			selectedFeature.onUpdateState = handleUpdateFeatureProps;
		}
	}

	/**
	 * Properties view management
	 */
	const handleCloseProperties = () => {
		let propertiesModal = document.getElementsByClassName("gedeonproperties_holder")[0];
		if(propertiesModal !== undefined){
			propertiesModal.classList.remove("active");
		}
	}

	const handleReduceProperties = (elt) => {
		console.log("reduce");
		console.log(elt);
		console.log(reducedElements);
		// Add element to footer if doesn't already exists
		if(!reducedElements.some(obj => obj.widgetId === elt.widgetId)){
			let newReducedElements = reducedElements.map(x => x);
			newReducedElements.push(elt);
			setReducedElements(newReducedElements);
			setPropertiesElement(null);
		}
		
		// reduce modal properties
		let propertiesModal = document.getElementsByClassName("gedeonproperties_holder")[0];
		if(propertiesModal !== undefined){
			propertiesModal.classList.remove("active");
		}
	}

	/*
	* Footer management
	*/
	const handleCloseFooterElement = (elt) => {
		console.log("close");
		console.log(elt);
		console.log(reducedElements);
		let newReducedElements = reducedElements.map(x => x);
		newReducedElements = newReducedElements.filter(x => x.widgetId !== elt.widgetId);
		setReducedElements(newReducedElements);
	}

	const handleOpenFooterElement = (elt) => {
		console.log("open");
		console.log(elt);
		console.log(reducedElements);
		setPropertiesElement(elt);
		let propertiesModal = document.getElementsByClassName("gedeonproperties_holder")[0];
		if(propertiesModal !== undefined){
			propertiesModal.classList.add("active");
		}
	}


	return (
		<div className="App">
			<div className="gedeon column">
				<div className="header">
					<GedeonHeader />
				</div>
				<div className="appcontent flex">
					<aside className="sidebar">
						<div className="navbar h column">
							<div className="column">
								{appViewLinks}
							</div>
							<div className="space"/>
							<div className="column">
								{adminAppViewLinks}
							</div>
						</div>
					</aside>
					<main className="content h flex" >
						<div className="tabs flex" role="tablist">
						{
							cacheTab.get().map((obj,index) => {
								let active = obj.isActive;
								let label = obj.label === undefined ? obj.name : obj.label;
								let close = null;
								if(obj.isClosable === undefined || obj.isClosable){
									close = (<img
												alt="X"
												title="close tab"
												src={ICON_CLOSE}
												className="activeIcon"
												onClick={(e) => {e.stopPropagation();onCloseTab(obj)}}
											/>);
								}
								return (
									<div key={index} onClick={() => onSelectTab(obj)}>
										<span className={"tab-item"+(active ? " active" : "")}>
											<span id={"gedeon-tab-"+index}>
												{label}
											</span>
											{close}
										</span>
									</div>
								);
								
							})
						}
						</div>
						{useFeature(selectedFeature)}
					</main>
				</div>
				<div className="footer">
					<Footer 
							reducedElements={reducedElements} 
							onCloseElement={handleCloseFooterElement} 
							onOpenElement={handleOpenFooterElement}/>
				</div>
			</div>
			{/* Properties modal */}
			<div className="gedeonproperties_holder">
				<DocumentProperties 
					item={propertiesElement}
					onCloseProperties={handleCloseProperties}
					onReduceProperties={handleReduceProperties}/>
			</div>
		</div>
	);
}

export default App;
