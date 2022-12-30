import React, { useState } from 'react';


export function GedeonSortable(props) {

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
		<div className='sortable'>
			<div className={props.active === 'up' ? 'up activated' : 'up'} onClick={() => onSort('up')}/>
			<div className={props.active === 'down' ? 'down activated' : 'down'} onClick={() => onSort('down')}/>
		</div>
	);
	
}