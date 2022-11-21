import React, { useState, useEffect } from 'react';

import Container from 'react-bootstrap/Container';
import Tab from 'react-bootstrap/Tab';
import Tabs from 'react-bootstrap/Tabs';

import GedeonContentList from '../widget/contentlist/GedeonContentList';

export function GedeonTabManager(){

	const [key] = useState(1);
	
	function setActiveKey(){
		console.log(key);
	}
	
	useEffect(() => {
		
	});

	return (
		<Tabs
		  id="gedeon-tab-manager"
		  activeKey={key}
		  onSelect={(key) => setActiveKey(key)}
		  className="mb-3"
		>
		  <Tab eventKey="1" title="Home">
			<Container>ABC</Container>
		  </Tab>
		  <Tab eventKey="2" title="Profile">
			<Container>ABC</Container>
		  </Tab>
		  <Tab eventKey="3" title="Contact" disabled>
			<Container>ABC</Container>
		  </Tab>
		</Tabs>
	);
	
}
export default GedeonTabManager;