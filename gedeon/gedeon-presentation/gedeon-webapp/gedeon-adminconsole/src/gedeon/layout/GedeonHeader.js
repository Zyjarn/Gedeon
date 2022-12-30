import { useState } from 'react';

import GedeonFileUpload from '../widget/fileupload/GedeonFileUpload.js';
import CurrentTime from '../widget/currenttime/CurrentTime.js';
import Properties from '../widget/properties/Properties.js';
import DocumentProperties from '../widget/docproperties/DocProperties.js';
import Modal from '../widget/modal/Modal.js';

import ICON_HELP from '../../images/help.svg';
import ICON_LOGOUT from '../../images/logout.svg';
import ICON_DOWNLOAD from '../../images/download.svg';
import ICON_SEARCH from '../../images/search.svg';

import './GedeonHeader.css';


function GedeonHeader() {

	/*<GedeonFileUpload />*/

	const handleAddDocument = () => {
		let propertiesModal = document.getElementsByClassName("gedeonproperties_holder")[0];
		if(propertiesModal !== undefined){
			propertiesModal.classList.add("active");
		}
	}



	return (
		<div className="headercontent flex">
			<div className="gedeonlogo">
				<img 
					alt="duck"
					title="COUAC!!"
					src="/gedeon.png"
				/>
			</div>
			
			<div className="flex w center">
				<div className="appinfo header-item column">
					<span>0.0.1-SNAPSHOT</span>
					<CurrentTime locale="en"/>
				</div>
				
				<div className="header-item">
					<button title="Add document(s)" onClick={() => handleAddDocument()}>
						<span className="maxi">Add document(s)</span>
						<img 
							alt="Add document(s)"
							title="Add document(s)"
							className="mini adddoc"
							src={ICON_DOWNLOAD} />
					</button>
				</div>
				
				<div className="space"/>
				
				<div className="quicksearch">
					<div className="flex center">
						<input placeholder="quick search"/>
						<button>
							<span className="maxi">Search</span>
							<img 
								alt="Quicksearch"
								title="Quicksearch"
								className="mini"
								src={ICON_SEARCH} />
						</button>
					</div>
				</div>
				
				<div className="space"/>
				
				<div className="header-item user">
					<div id="username" className="bold">Jeanine Fandango</div>
					<div className="logout">
						<img 
							alt="logout"
							title="logout"
							src={ICON_LOGOUT}
						/>Logout
					</div>
				</div>
				
				<div className="header-item help maxi">
					<img 
						alt="help"
						src={ICON_HELP}
					/>
				</div>
			</div>
		</div>
	);
}
export default GedeonHeader;