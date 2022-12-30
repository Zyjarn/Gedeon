import { useState } from 'react';

export function Selector1(props) {

	return (
		<div className="column">
			<div className="flex textSizeL bold">
				Selector 1
			</div>
			<table>
				<tbody>
					<tr>
						<td><div>Root id</div></td>
						<td><input /></td>
					</tr>
					<tr>
						<td><div>Leaf id</div></td>
						<td><input /></td>
					</tr>
					<tr>
						<td><div>Second Leaf id</div></td>
						<td><input /></td>
					</tr>
				</tbody>
			</table>
			<div className="flex">
				<button >Search</button>
				<div className="space"/>
				<button >Reset</button>
			</div>
		</div>
	);
	
}
export default Selector1;