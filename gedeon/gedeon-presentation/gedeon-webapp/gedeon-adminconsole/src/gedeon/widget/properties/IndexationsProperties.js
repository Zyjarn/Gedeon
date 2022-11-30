import {useState} from 'react';

import FAC from './indexationstabs/FAC';

import CLOSE_ICON from '../../../images/close.svg';

export function IndexationsProperties(props) {

	const[indexUseCase] = useState(2);
	
	let listUseCases = [
		{
			"label":"Treaty",
			"name":"TRT",
			"value":1
		},
		{
			"label":"Facultative",
			"name":"FAC",
			"value":2
		},
		{
			"label":"Event",
			"name":"EVT",
			"value":3
		},
		{
			"label":"Retrocession",
			"name":"RET",
			"value":4
		}
	];
	
	let radioListUseCase = listUseCases.map((obj,index) => (
		<div>
			<input type="radio" id={"useCase"+obj.name}
				name="indexUseCase" value={obj.value} checked={ index === obj.value}/>
			<label for={"useCase"+obj.name}>{obj.label}</label>
		</div>
	));

	return (
		<div>
			<div className="gedeonflex">
				{radioListUseCase}
			</div>
			<hr />
			<FAC />
		</div>
	);
	
}
export default IndexationsProperties;