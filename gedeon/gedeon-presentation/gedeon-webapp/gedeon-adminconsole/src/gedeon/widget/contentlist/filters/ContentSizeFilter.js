import { useState } from 'react';

export function ContentSizeFilter(props) {
	
	let [type, setType] = useState(props.type === undefined ? "" : props.type);
	
	const selectUnit = (<select size="1" className="">
				<option key={0} value="B">B</option>
				<option key={1} value="KB">KB</option>
				<option key={2} value="MB">MB</option>
				<option key={3} value="GB">GB</option>
				<option key={4} value="TB">TB</option>
			</select>);
	
	let inputs = [];
	if(type === ">" || type === "<" || type === "="){
		inputs.push(<input key={1} type="number" size="1" />);
		inputs.push(selectUnit);
	} else if (type === "B"){
		inputs.push(<input key={1} type="number" size="1" />);
		inputs.push(selectUnit);
		inputs.push(<input key={3} type="number" size="1" />);
		inputs.push(selectUnit);
	}

	return (
		<div className="filter contentsize flex space">
			<div className="space"/>
			<select key={0} size="1" className="" onChange={(e) => setType(e.target.value)}>
				<option key={0} value=""></option>
				<option key={1} value=">">&ge;</option>
				<option key={2} value="<">&le;</option>
				<option key={3} value="=">=</option>
				<option key={4} value="B">Between</option>
			</select>
			{inputs}
			<div className="space"/>
		</div>
	)
}
export default ContentSizeFilter;