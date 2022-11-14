import React, { Component } from 'react';
import Navbar from 'react-bootstrap/Navbar';
import Container from 'react-bootstrap/Container';
import OverlayTrigger from 'react-bootstrap/OverlayTrigger';
import Form from 'react-bootstrap/Form';
import Tooltip from 'react-bootstrap/Tooltip';

class GedeonHeader extends Component {

	constructor(props) {
		super(props);
	}

	componentDidMount() {

	}

	render() {

		return (
			<div>
				<AppNavbar bg="light" expand="lg"></AppNavbar>
				<Container fluid>
					<Navbar.Brand href="#home">Hello</Navbar.Brand>
					<Navbar.Toggle />
					<Form className="c-flex">
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
			</div>
		);
	}
}
export default ClientList;