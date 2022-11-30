import {useState} from 'react';

import './App.css';
import GedeonHeader from "./gedeon/layout/GedeonHeader"
import GedeonNavBar from './gedeon/layout/GedeonNavBar';
import GedeonTabView from './gedeon/layout/GedeonTabView';

import ICON_FAVORITES from './images/favorites.svg';
import ICON_FOLDER from './images/folder.svg';
import ICON_SEARCH from './images/search.svg';
import ICON_TREEVIEW from './images/treeview.svg';
import ICON_SETTINGS from './images/settings.svg';

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
					"icon":ICON_FAVORITES,
					"isOpenNewTab":false,
					"isClosable":false
				},
				"folder":{
					"name":"folder",
					"icon":ICON_FOLDER,
					"isOpenNewTab":false
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
	
	const [tabs,setTabs] = useState([config.appViewsConfig[config.appViews[0].name]]);
	const [activeTab,setActiveTab] = useState(0);
  
	tabs[0].isActive = true;

	
	function addTab(appView){
		console.log("addTab");
		let tabconfig = config.appViewsConfig[appView];
		let add = true;
		let index = 0;
		let count = 0;
		for(var i in tabs){
			//Disaable active on all tabs
			tabs[i].isActive = false
			if(tabs[i].name === tabconfig.name){
				//check if tab must be added or re-open
				add = tabconfig.isOpenNewTab;
				//if tab must be re-open keep track of index key
				index = i;
				count++;
			}
		}
		
		tabconfig.label = tabconfig.name;
		if(add && count > 0){
			tabconfig.label += " ("+count+")";
		}
		
		if(add){
			tabconfig.isActive = true;
			console.log("addTab");
			setTabs(tabs);
		} else {
			setActiveTab(index);
		}
	}
	
	function onSelectTab(index){
		console.log("onSelect");
		for(var i in tabs){
			//Disaable active on all tabs
			tabs[i].isActive = false;
			if(i === index){
				tabs[i].isActive = true;
			}
		}
		setTabs(tabs);
	}
	
	function onCloseTab(index){
		console.log("onClose");
		let newTab = tabs;
		newTab.splice(index,1);
		setTabs(newTab);
	}
	

	return (
		<div className="App">
			<div className="gedeon">
				<header className="header">
					<GedeonHeader />
				</header>
				<aside className="sidebar">
					<GedeonNavBar config={config} callback={addTab}/>
				</aside>
				<main className="content" >
					<GedeonTabView tabs={tabs} onCloseTab={onCloseTab} onSelectTab={onSelectTab}/>
				</main>
				<footer className="footer">
					footer
				</footer>
			</div>
		</div>
	);
}

export default App;
