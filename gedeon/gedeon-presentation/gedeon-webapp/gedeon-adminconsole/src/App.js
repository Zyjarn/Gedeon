import {useState} from 'react';

import './App.css';
import GedeonHeader from "./gedeon/layout/GedeonHeader"

import Favorites from './gedeon/features/Favorites';
import Search from './gedeon/features/Search';

import GedeonContentList from './gedeon/widget/contentlist/GedeonContentList';

import ConfigAPI from './gedeon/api/ConfigAPI';

import ICON_FAVORITES from './images/favorites.svg';
import ICON_FOLDER from './images/folder.svg';
import ICON_SEARCH from './images/search.svg';
import ICON_TREEVIEW from './images/treeview.svg';
import ICON_SETTINGS from './images/settings.svg';

import ICON_CLOSE from './images/close.svg';

function App() {
	/*const [state,setState] = useState({loading:true,tabs:[]});
	/*const [tabs,setTabs] = useState([]);
	
	let tabs = state.tabs;*/
	
	function handleGetConfig(config) {
		/*setState({
			loading:false,
			tabs: [config.appViewsConfig[config.appViews[0].name]]
		})*/
		/*isLoading(false);
		tabs = [config.appViewsConfig[config.appViews[0].name]];*/
	}

	/*if(state.loading){
		ConfigAPI.getConfig("toto", handleGetConfig);
	}
*/
	
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
					"icon":ICON_FAVORITES,
					"isActive":true,
					"isOpenNewTab":false,
					"isClosable":false,
					"content": <Favorites />
				},
				"folder":{
					"name":"folder",
					"icon":ICON_FOLDER,
					"isOpenNewTab":false,
					"isClosable":false,
					"content": <GedeonContentList />
				},
				"search":{
					"name":"search",
					"icon":ICON_SEARCH,
					"isOpenNewTab":true,
					"content": <Search />
				},
				"treeview":{
					"name":"treeview",
					"icon":ICON_TREEVIEW,
					"isOpenNewTab":true,
					"content": <GedeonContentList />
				},
				"settings" : {
					"name":"settings",
					"icon":ICON_SETTINGS,
					"isOpenNewTab":false,
					"content": <GedeonContentList />
				}
			},
		
			adminAppViews : [
				{
					"name":"settings",
					"icon":ICON_SETTINGS
				}
			]
		};
	


	const [state,setState] = useState({loading:true,tabs:[config.appViewsConfig[config.appViews[0].name]]});
	/*const [tabs,setTabs] = useState([]);*/
	
	let tabs = state.tabs;


	
	function addTab(appView){
		console.log("addTab");
		let tabconfig = config.appViewsConfig[appView];
		let add = true;
		let index = 0;
		let count = 0;
		let newTabs = tabs.map((x) => x)
		for(var i in newTabs){
			//Disaable active on all tabs
			newTabs[i].isActive = false
			if(newTabs[i].name === tabconfig.name){
				//check if tab must be added or re-open
				add = tabconfig.isOpenNewTab;
				//if tab must be re-open keep track of index key
				index = i;
				count++;
			}
		}
		
		tabconfig.label = tabconfig.name;
		
		if(add){
			tabconfig.isActive = true;
			newTabs.push(tabconfig);
			if(count > 0){
				tabconfig.label += " ("+count+")";
			}
		} else {
			newTabs[index].isActive = true;
		}
		setState({
			loading:false,
			tabs: newTabs
		})
	}
	
	function onSelectTab(index){
		console.log("onSelect");
		let newTabs = tabs.map((x,i) => {x.isActive = i===index;return x;})
		setState({
			loading:false,
			tabs: newTabs
		})
	}
	
	
	function onCloseTab(index){
		console.log("onClose");
		let newTabs = state.tabs;
		let deletedTab = newTabs.splice(index,1)[0];
		if(deletedTab.isActive){
			newTabs.forEach((x) => x.isActive = false);
			newTabs[index-1].isActive = true;
		}
		setState({
			loading:false,
			tabs: newTabs
		})
	}
	
	
		/**
	* Get an image node with for the navigation bar 36x36
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

console.log("render");
console.log(tabs);
console.log("---------");

	let selectedTab = null;
	for(var i in state.tabs){
		if(state.tabs[i].isActive){
			selectedTab = state.tabs[i];
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
						<div className="navbar hcolumn">
							<div className="column">
								{appViewLinks}
							</div>
							<div className="space"/>
							<div className="column">
								{adminAppViewLinks}
							</div>
						</div>
					</aside>
					<main className="content hflex" >
						<div className="tabs flex" role="tablist">
						{
							tabs.map((obj,index) => {
								let active = obj.isActive;
								let label = obj.label === undefined ? obj.name : obj.label;
								let close = null;
								if(obj.isClosable === undefined || obj.isClosable){
									close = (<img
												alt=""
												src={ICON_CLOSE}
												className="activeIcon"
												onClick={() => onCloseTab(index)}
											/>);
								}
								return (
									<div key={index}>
										<span className={"tab-item"+(active ? " active" : "")}>
											<span id={"gedeon-tab-"+index} onClick={() => onSelectTab(index)}>
												{label}
											</span>
											{close}
										</span>
									</div>
								);
								
							})
						}
						</div>
						<div className="feature">
							{selectedTab.content}
						</div>
					</main>
				</div>
				<div className="footer">
					footer
				</div>
			</div>
		</div>
	);
}

export default App;
