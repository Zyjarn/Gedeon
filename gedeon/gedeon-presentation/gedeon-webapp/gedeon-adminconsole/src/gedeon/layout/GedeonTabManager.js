import React, { Component } from 'react';
import Container from 'react-bootstrap/Container';
import Tab from 'react-bootstrap/Tab';
import Tabs from 'react-bootstrap/Tabs';

import GedeonContentList from '../widget/contentlist/GedeonContentList';

class GedeonTabManager extends Component {

	constructor(props) {
		super(props);
		this.state = {
		  key: 1
		};
	}

	componentDidMount() {
		
	}
	
	handleSelect = (key) => {
		this.setState({ key });
	}

	render() {

		return (
			<Tabs
			  id="gedeon-tab-manager"
			  activeKey={this.state.key}
			  onSelect={this.handleSelect}
			  className="mb-3"
			>
			  <Tab eventKey="1" title="Home">
				<Container>ABC</Container>
			  </Tab>
			  <Tab eventKey="2" title="Profile">
				<GedeonContentList />
			  </Tab>
			  <Tab eventKey="3" title="Contact" disabled>
				<Container>ABC</Container>
			  </Tab>
			</Tabs>
		);
	}
}
export default GedeonTabManager;