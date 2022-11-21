import React, { Component } from 'react';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';

import ICON_FAVORITES from './images/favorites.svg';
import ICON_FOLDER from './images/folder.svg';
import ICON_SEARCH from './images/search.svg';
import ICON_TREEVIEW from './images/treeview.svg';
import ICON_SETTINGS from './images/settings.svg';

import './GedeonNavBar.css';

class GedeonNavBar extends Component {

	constructor(props) {
		super(props);
		
		//TODO To externalize
		//List of configured app view
		this.appViews = [
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
		]
		
		this.adminAppViews = [
			{
				"name":"settings",
				"icon":ICON_SETTINGS
			}
		]
		
		//Set active item as favorites
		this.state = {selectedItem:"favorites"};
		
		//Callback used to send the id of the selected item in navigation to the parent component
		//this.callbackSelect = props.callbackSelect;
		//this.callbackSelect(e);
	}

	componentDidMount() {

	}
	
	onSelectItem = (name) => {
		console.log(name);
		this.setState({selectedItem:name});
	}
	
	/**
	* Get an image node with for the navigation bar 36x36
	*
	* @param appViewDef	AppViewDef( @param name	feature name, @param icon	imported svg or target image file)
	*/
	getAppViewLink = (appViewDef) => {
		let className = "nav-link";
		if(appViewDef.name === this.state.selectedItem){
			className += " active";
		}
		
		return ( 
			<a className={className} onClick={this.onSelectItem(appViewDef.name)}>
				<img
					alt={appViewDef.name}
					src={appViewDef.icon}
					width="36"
					height="36" 
				/>
			</a>
		);
	}

	
	
	render() {
		
		
		let appViewLinks = [];
		this.appViews.map((obj, index) => (
			appViewLinks.push(this.getAppViewLink(obj))
		));
		
		let adminAppViewLinks = [];
		this.adminAppViews.map((obj, index) => (
			adminAppViewLinks.push(this.getAppViewLink(obj))
		));

		return (
			<div className="gedeon-navbar">
				<div className="sub">
					{appViewLinks}
				</div>
				<div className="extend"/>
				<div className="sub">
					{adminAppViewLinks}
				</div>
			</div>
			
		);
	}
	
}
export default GedeonNavBar;