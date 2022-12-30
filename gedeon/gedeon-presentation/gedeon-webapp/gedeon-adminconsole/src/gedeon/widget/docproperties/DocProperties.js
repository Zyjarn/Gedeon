import { useState } from 'react';

import ClassProperties from './ClassProperties';
import ObjectPropertiesSecurity from './ObjectPropertiesSecurity';

import ICON_PLUS from './images/plus.svg';
import ICON_MINUS from './images/minus.svg';
import ICON_REFRESH from '../../../images/refresh.svg';

import ICON_CPLUS from './images/cplus.svg';
import ICON_COPY from './images/copy.svg';
import ICON_PASTE from './images/paste.svg';

import ICON_CLOSE from '../../../images/close.svg';
import ICON_VIEW from '../../../images/view.svg';
import ICON_VERSIONS from '../../../images/versions.svg';
import ICON_DOWNLOAD from '../../../images/download.svg';

import './Properties.css';

/**
 * Display properties of the given item or create a new one
 * @param {*} props 
 * @returns 
 */
export function Properties(props) {

	let isAddition = props.item === undefined || props.item === null ? true : props.item.isAddition;
	let item = props.item === undefined || props.item === null ? 
			{
				label: isAddition ? "Add Document" : "Properties",
				isAddition : isAddition,
				widgetId : (Math.random() + 1).toString(36).substring(5)
			} 
			: props.item;
	

	let entryTemplate = {
		columns:[
			{
				"name": {
					"label":"Name",
					"name":"name",
					"type":"string",
					"mandatory":true
				},
				"nature": {
					"label":"Nature",
					"name":"nature",
					"type":"select"
				},
				"type": {
					"label":"Type",
					"name":"name",
					"type":"string"
				},
				"type2": {
					"label":"Type2",
					"name":"type2",
					"type":"string"
				}
			},
			{
				"name": {
					"label":"Name",
					"name":"name",
					"type":"string",
					"mandatory":true
				},
				"nature": {
					"label":"Nature",
					"name":"nature",
					"type":"select"
				},
				"type": {
					"label":"Type",
					"name":"name",
					"type":"string"
				},
				"type2": {
					"label":"Type2",
					"name":"type2",
					"type":"string"
				}
			}
		]
	};

	const [reduceGeneralProperties, setReduceGeneralProperties] = useState(false);

	const [reduceDocumentProperties, setReduceDocumentProperties] = useState(false);

	const [reduceSecurity, setReduceSecurity] = useState(false);

	const [reduceInvolvedDocument, setReduceInvolvedDocument] = useState(false);

	

	return (
		<div className="gedeonproperties">
			<div className="propertiesfield textSizeXL bold">{isAddition ? "Add document" : "Properties"}</div>

			<div className="content">			

				<div>

					<div className="propertiesfield generalproperties">
						<div className="gedeonflex fieldTitle">
							<img
								alt="reduce general properties tab"
								title="reduce general properties tab"
								src={reduceGeneralProperties ? ICON_PLUS : ICON_MINUS}
								onClick={() => setReduceGeneralProperties(!reduceGeneralProperties)}
							/>
							<span>General Properties</span>
						</div>
						<div className={"propertiesfield-content" + (reduceGeneralProperties ? " reduce" : "")}>
							<table>
								<tbody>
									<tr>
										<td><span className="label">Document class</span></td>
										<td></td>
										<td>
											<select >
												<option>Document</option>
											</select>
										</td>
									</tr>
									<tr>
										<td><span className="label">Save In</span></td>
										<td></td>
										<td>
											<select >
												<option>Unfiled</option>
											</select>
										</td>
									</tr>
									<tr>
										<td><span className="label">Selected File(s)</span></td>
										<td></td>
										<td>
											<input type="file" onChange={(e) => console.log(e.target.files)} multiple/>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>

					<div className="propertiesfield docclassproperties">
						<div className="gedeonflex fieldTitle">
							<img
								alt="reduce properties tab"
								title="reduce properties tab"
								src={reduceDocumentProperties ? ICON_PLUS : ICON_MINUS}
								onClick={() => setReduceDocumentProperties(!reduceDocumentProperties)}
							/>
							<span>Document Properties</span>
						</div>
						<div className={"propertiesfield-content" + (reduceDocumentProperties ? " reduce" : "")}>
							<ClassProperties entryTemplate={entryTemplate}/>
						</div>
					</div>


					<div className="propertiesfield security">
						<div className="gedeonflex fieldTitle">
							<img
								alt="reduce confidentiality tab"
								title="reduce confidentiality tab"
								src={reduceSecurity ? ICON_PLUS : ICON_MINUS}
								onClick={() => setReduceSecurity(!reduceSecurity)}
							/>
							<span>Security</span>
						</div>
						<div className={"propertiesfield-content" + (reduceSecurity ? " reduce" : "")}>
							<ObjectPropertiesSecurity />
						</div>
					</div>



					<div className="propertiesfield systemproperties">
						<div className="gedeonflex fieldTitle">
							<img
								alt="reduce involved documents tab"
								title="reduce involved documents tab"
								src={reduceInvolvedDocument ? ICON_PLUS : ICON_MINUS}
								onClick={() => setReduceInvolvedDocument(!reduceInvolvedDocument)}
							/>
							<span>Involved document(s)</span>
						</div>
						<div className={"propertiesfield-content" + (reduceInvolvedDocument ? " reduce" : "")}>

							<table>
								<tbody>
									<tr>
										<td>Filename</td>
										<td></td>
									</tr>
									<tr>
										<td>Content Size</td>
										<td></td>
									</tr>
									<tr>
										<td>Content Type</td>
										<td></td>
									</tr>
									<tr>
										<td>Id</td>
										<td></td>
									</tr>
								</tbody>
							</table>
						</div>


					</div>

				</div>





			</div>
			<div className="propertiesactions flex">
				<button>Save</button>
				<div className='space'></div>
				<button onClick={() => props.onReduceProperties(item)}>Reduce</button>
				<button onClick={() => props.onCloseProperties()}>Cancel</button>
				
			</div>
		</div>
	);

}
export default Properties;