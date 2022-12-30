import {useState} from 'react';

import HELP_ICON from '../../../images/help.svg';
import ICON_CLOSE from '../../../images/close.svg';

export function ObjectPropertiesSecurity(props) {
	
	/*Security*/
	let creators = Array.from({length:21}).map(index => (
		<div key={index} className="security-elt flex center">
			<img
				alt="remove security element"
				title="remove security element"
				src={ICON_CLOSE}
				className="activehover"
			/>
			<span>{"Group 1"}</span>
		</div>
	));
	let owners = Array.from({length:21}).map(index => (
		<div key={index} className="security-elt flex center">
			<img
				alt="remove security element"
				title="remove security element"
				src={ICON_CLOSE}
				className="activehover"
			/>
			<span>{"Group 1"}</span>
		</div>
	));
	let viewer = Array.from({length:21}).map(index => (
		<div key={index} className="security-elt flex center">
			<img
				alt="remove security element"
				title="remove security element"
				src={ICON_CLOSE}
				className="activehover"
			/>
			<span>{"Group 1"}</span>
		</div>
	));
	let readers = Array.from({length:21}).map(index => (
		<div key={index} className="security-elt flex center">
			<img
				alt="remove security element"
				title="remove security element"
				src={ICON_CLOSE}
				className="activehover"
			/>
			<span>{"Group 1"}</span>
		</div>
	));

	return (
		<div className='w'>
			<table>
				<tbody>
					<tr>
						<td>Creator</td>
						<td>
							<div className="flex wrap">{creators}</div>
						</td>
					</tr>
					<tr>
						<td>Owner</td>
						<td>
							<div className="flex wrap">{owners}</div>
						</td>
					</tr>
					<tr>
						<td>Reader</td>
						<td>
							<div className="flex wrap">{viewer}</div>
						</td>
					</tr>
					<tr>
						<td>Read-Only</td>
						<td>
							<div className="flex wrap">{readers}</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	);
	
}
export default ObjectPropertiesSecurity;