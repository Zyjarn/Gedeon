import React, { useState } from 'react';

import ICON_CLOSE from '../../../images/close.svg';

export function SearchCriteria(props) {
	const [value] = useState(props.criteria.value === undefined ? "" : props.criteria.value);
	
	return (
		<div className="searchCriteria">
			<span className="label">{props.criteria.label}</span>
			<input type="text" className="value" 
				onChange={event => props.onChange(props.criteria.criteria, event.target.value)}
				value={props.criteria.value}
			/>
			<img
				alt="close criteria"
				src={ICON_CLOSE}
				className="activeIcon"
				onClick={() => props.remove(props.criteria.criteria)}
			/>
		</div>
	);
	
}
export default SearchCriteria;
