import {useState} from 'react';

import CLOSE_ICON from '../../../../images/close.svg';
import CLOSE_FAVORITE from '../../../../images/favorite.svg';

export function FAC(props) {

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
		<div className="gedeonflex">
			<div className="gedeoncolumn">
				<table>
					<tbody>
						<tr>
							<td>Nature</td>
							<td>*</td>
							<td>
								<select>
									<option></option>
									<option>Technical and underwriter</option>
								</select>
							</td>
							<td/>
							<td/>
						</tr>
						<tr>
							<td>Client</td>
							<td>*</td>
							<td>
								<select>
									<option></option>
									<option>Technical and underwriter zadazd azazd</option>
								</select>
							</td>
							<td>
								<img 
									alt={"select favorite"}
									title={"select favorite"}
									src={CLOSE_FAVORITE}
								/>
							</td>
							<td>
								<img 
									alt={"remove value"}
									title={"remove value"}
									src={CLOSE_ICON}
								/>
							</td>
						</tr>
						<tr>
							<td>Facultative</td>
							<td>*</td>
							<td>
								<select>
									<option></option>
									<option>Technical and underwriter zadazd azazd</option>
								</select>
							</td>
							<td>
								<img 
									alt={"select favorite"}
									title={"select favorite"}
									src={CLOSE_FAVORITE}
								/>
							</td>
							<td>
								<img 
									alt={"remove value"}
									title={"remove value"}
									src={CLOSE_ICON}
								/>
							</td>
						</tr>
						
						<tr>
							<td>UWY</td>
							<td>*</td>
							<td>
								<select>
									<option></option>
									<option>2022</option>
								</select>
							</td>
							<td>
								
							</td>
							<td>
								
							</td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td>
								<div className="gedeonflex">
									<input value="2017" disabled/>
									<img 
										alt={"remove value"}
										title={"remove value"}
										src={CLOSE_ICON}
									/>
								</div>
							</td>
							<td></td>
							<td></td>
						</tr>
						
						<tr>
							<td>Display on all contracts</td>
							<td></td>
							<td>
								<input type="checkbox" />
							</td>
							<td></td>
							<td></td>
						</tr>
						
						<tr>
							<td>Indexation Units</td>
							<td>*</td>
							<td>
								<input disabled/>
							</td>
							<td></td>
							<td></td>
						</tr>
						
						<tr>
							<td>REB Number</td>
							<td>*</td>
							<td>
								<input disabled/>
							</td>
							<td></td>
							<td></td>
						</tr>
						
						<tr>
							<td>Cedent Period - From</td>
							<td>*</td>
							<td>
								<input placeholder="MM/YYYY" />
							</td>
							<td>
								
							</td>
							<td>
								
							</td>
						</tr>
						<tr>
							<td>Cedent Period - To</td>
							<td>*</td>
							<td>
								<input placeholder="MM/YYYY" />
							</td>
							<td>
								
							</td>
							<td>
								
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<div className="gedeoncolumn">
				<table>
					<tbody>
						<tr>
							<td>Type</td>
							<td>*</td>
							<td>
								<select>
									<option></option>
									<option>Technical and underwriter</option>
								</select>
							</td>
							<td/>
							<td/>
						</tr>
						<tr>
							<td>Master</td>
							<td>*</td>
							<td>
								<select>
									<option></option>
									<option>Technical and underwriter adzdadza</option>
								</select>
							</td>
							<td>
								<img 
									alt={"select favorite"}
									title={"select favorite"}
									src={CLOSE_FAVORITE}
								/>
							</td>
							<td>
								<img 
									alt={"remove value"}
									title={"remove value"}
									src={CLOSE_ICON}
								/>
							</td>
						</tr>
						<tr>
							<td>Individual Claim</td>
							<td>*</td>
							<td>
								<select>
									<option></option>
									<option>Technical and underwriter adzdadza</option>
								</select>
							</td>
							<td>
								<img 
									alt={"select favorite"}
									title={"select favorite"}
									src={CLOSE_FAVORITE}
								/>
							</td>
							<td>
								<img 
									alt={"remove value"}
									title={"remove value"}
									src={CLOSE_ICON}
								/>
							</td>
						</tr>
						<tr>
							<td>Insured</td>
							<td>*</td>
							<td>
								<select>
									<option></option>
									<option>Technical and underwriter adzdadza</option>
								</select>
							</td>
							<td>
								<img 
									alt={"select favorite"}
									title={"select favorite"}
									src={CLOSE_FAVORITE}
								/>
							</td>
							<td>
								<img 
									alt={"remove value"}
									title={"remove value"}
									src={CLOSE_ICON}
								/>
							</td>
						</tr>
						<tr>
							<td>Ledger</td>
							<td>*</td>
							<td>
								<input disabled/>
							</td>
							<td>
								
							</td>
							<td>
								
							</td>
						</tr>
						<tr>
							<td>Key words</td>
							<td>*</td>
							<td>
								<select>
									<option></option>
									<option>Technical and underwriter adzdadza</option>
								</select>
							</td>
							<td>
								
							</td>
							<td>
								
							</td>
						</tr>
						
						<tr>
							<td>Settlements</td>
							<td>*</td>
							<td>
								<select>
									<option></option>
									<option>R</option>
									<option>S</option>
								</select>
								<input />
							</td>
							<td>
								
							</td>
							<td>
								
							</td>
						</tr>
						
						<tr>
							<td>SCOR Period - From</td>
							<td>*</td>
							<td>
								<input placeholder="MM/YYYY" />
							</td>
							<td>
								
							</td>
							<td>
								
							</td>
						</tr>
						<tr>
							<td>SCOR Period - To</td>
							<td>*</td>
							<td>
								<input placeholder="MM/YYYY" />
							</td>
							<td>
								
							</td>
							<td>
								
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			
		</div>
	);
	
}
export default FAC;