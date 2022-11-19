import React, { Component } from 'react';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';

class GedeonNavBar extends Component {

	constructor(props) {
		super(props);
	}

	componentDidMount() {

	}

	render() {

		return (
			<Navbar bg="light">
				<Container fluid>
					<Navbar.Collapse id="basic-navbar-nav">
						<Nav defaultActiveKey="/home" className="flex-column">
						  <Nav.Link href="/home">Active</Nav.Link>
						  <Nav.Link eventKey="link-1">Link</Nav.Link>
						  <Nav.Link eventKey="link-2">Link</Nav.Link>
						  <Nav.Link eventKey="disabled" disabled>
							Disabled
						  </Nav.Link>
						</Nav>
					</Navbar.Collapse>
				</Container>
			</Navbar>
		);
	}
}
export default GedeonNavBar;