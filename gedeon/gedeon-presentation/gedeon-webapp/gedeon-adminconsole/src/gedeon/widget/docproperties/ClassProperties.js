import {useState} from 'react';


import CLOSE_ICON from '../../../images/close.svg';

export function ClassProperties(props) {

	/**
	 * Get a tr element from the given property definition object 
	 * @param {object} propDef 
	 * @returns 
	 */
	const getPropertyRow = (propDef,index) => {

		let content = <input/>;
		if(propDef.type === "string"){
			content = <input className='w' placeholder={propDef.placeholder}/>;
		} else if(propDef.type === "select"){
			content = <select className='w'>
				<option></option>
			</select>;
		}

		return (
			<tr key={propDef.name+"index"}>
				<td className={""+(propDef.mandatory === true ? " mandatory":"")}>{propDef.label}</td>
				<td><div className='w'>{content}</div></td>
			</tr>
		);
	}

	return (
		<div className="flex">
			{
				props.entryTemplate.columns.map((column, index) => (
						<table key={index} className='space'>
							<tbody>
								{
									Object.values(column).map((obj,index) => getPropertyRow(obj,index))
								}
							</tbody>
						</table>))
			}
		</div>
	);
	
}
export default ClassProperties;