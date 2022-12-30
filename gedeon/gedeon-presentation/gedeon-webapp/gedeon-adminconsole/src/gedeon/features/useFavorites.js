import React, { useState } from 'react';

import GedeonContentList from '../widget/contentlist/GedeonContentList';

import './Favorites.css';

export function useFavorites(props) {

	const [columnDef] = useState(props.columnDef);
	
	/*
	* event when page change
	*/
	function onSort(up){
		if(props.onSort !== undefined){
			props.onSort({columnDef:columnDef,active:up});
		}
	}
	
	return (
		<div className='gedeon-favorites'>
			<GedeonContentList />
		</div>
	);
	
}
export default useFavorites;
