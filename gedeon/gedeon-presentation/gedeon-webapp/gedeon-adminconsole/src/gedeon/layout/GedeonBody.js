import React, { Component } from 'react';
import Container from 'react-bootstrap/Container';
import GedeonNavBar from './GedeonNavBar';
import GedeonTabManager from './GedeonTabManager';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import './GedeonBody.css';

class GedeonBody extends Component {

	constructor(props) {
		super(props);
	}

	componentDidMount() {

	}

	render() {

		return (
			<Container fluid className="gedeonbody h-100">
				<Row className="h-100">
					<Col sm={1}>
						<GedeonNavBar />
					</Col>
					<Col sm={11}>
						<GedeonTabManager />
					</Col>
				</Row>
			</Container>
			
		);
		/*
		<div className='gedeonbody d-inline-block w-100'>
				<GedeonNavBar />
				<GedeonTabManager />
			</div>
		<Container fluid className="gedeonbody">
				<Row>
					<Col sm={1}>
						<GedeonNavBar />
					</Col>
					<Col  sm={10}>
						<GedeonTabManager />
					</Col>
				</Row>
			</Container>
		*/
	}
}
export default GedeonBody;