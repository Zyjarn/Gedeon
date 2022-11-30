import { useState } from 'react';

import GedeonFileUpload from '../widget/fileupload/GedeonFileUpload.js';
import CurrentTime from '../widget/currenttime/CurrentTime.js';
import Properties from '../widget/properties/Properties.js';

import ICON_HELP from '../../images/help.svg';
import ICON_LOGOUT from '../../images/logout.svg';

import './GedeonHeader.css';


function GedeonHeader() {

	/*<GedeonFileUpload />*/

	return (
		<div className="headercontent flex">
			<div className="gedeonlogo">
				<img 
					alt="duck"
					title="COUAC!!"
					src="/gedeon.png"
				/>
			</div>
			<div className="header-item column">
				<span>0.0.1-SNAPSHOT</span>
				<CurrentTime locale="en"/>
			</div>
			
			<div className="header-item">
				<button>Add document(s)</button>
				<div className="gedeonproperties_holder"><Properties isAddition /></div>
			</div>
			
			<div className="space"/>
			
			<div className="quickSearch">
				<div>
					<input placeholder="quick search"/>
					<button>Search</button>
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
			
			<div className="header-item help">
				<img 
					alt="help"
					src={ICON_HELP}
				/>
			</div>
		</div>
	);
}
export default GedeonHeader;