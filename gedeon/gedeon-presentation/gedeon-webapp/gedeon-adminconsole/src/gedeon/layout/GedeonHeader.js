import React, { Component } from 'react';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import NavDropdown from 'react-bootstrap/NavDropdown';
import OverlayTrigger from 'react-bootstrap/OverlayTrigger';
import Tooltip from 'react-bootstrap/Tooltip';

import GedeonFileUpload from '../widget/fileupload/GedeonFileUpload.js';

import './GedeonHeader.css';


class GedeonHeader extends Component {

	constructor(props) {
		super(props);
	}

	componentDidMount() {

	}

	render() {

		return (
			
				<Navbar bg="light" expand="lg">
					<Container fluid>
					
						<Navbar.Brand href="#home">
							<img
							  alt=""
							  src="/gedeon.png"
							  width="60"
							  height="60"
							  className="d-inline-block align-top"
							/>{' '}
						</Navbar.Brand>
						
						<Navbar.Collapse id="basic-navbar-nav">
						  <Nav className="me-auto">
							<Nav.Link href="#home">Home</Nav.Link>
							<Nav.Link href="#link">Link</Nav.Link>
							<NavDropdown title="Dropdown" id="basic-nav-dropdown">
							  <NavDropdown.Item href="#action/3.1">Action</NavDropdown.Item>
							  <NavDropdown.Item href="#action/3.2">
								Another action
							  </NavDropdown.Item>
							  <NavDropdown.Item href="#action/3.3">Something</NavDropdown.Item>
							  <NavDropdown.Divider />
							  <NavDropdown.Item href="#action/3.4">
								Separated link
							  </NavDropdown.Item>
							</NavDropdown>
						  </Nav>
						</Navbar.Collapse>
						<GedeonFileUpload />
						<Form className="d-inline-flex">
							<Form.Control
								type="search"
								placeholder="Search"
								className="me-2"
								aria-label="Search"
							/>
							<Button variant="outline-success">Search</Button>
						</Form>
						<Navbar.Collapse className="justify-content-end">
							<NavDropdown title="Dropdown" id="basic-nav-dropdown">
								<NavDropdown.Item href="#action/preferences">Preferences</NavDropdown.Item>
								<NavDropdown.Divider />
								<NavDropdown.Item href="#action/logout">
									Logout
								</NavDropdown.Item>
							</NavDropdown>
						</Navbar.Collapse>
						<OverlayTrigger className="justify-content-end"
							key="bottom"
							placement="bottom"
							overlay={
								<Tooltip id={`tooltip-about`}>
									About.
								</Tooltip>
							}
						>
							<img
									alt=""
									src="../../logo.svg"
									width="30"
									height="30"
									className="d-inline-block align-top"
								/>
						</OverlayTrigger>


					</Container>
				</Navbar>
		);
	}
}
export default GedeonHeader;