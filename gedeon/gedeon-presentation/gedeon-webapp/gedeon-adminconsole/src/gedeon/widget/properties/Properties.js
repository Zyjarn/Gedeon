import {useState} from 'react';

import IndexationsProperties from './IndexationsProperties';
import Confidentiality from './Confidentiality';

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

export function Properties(props) {

	let isAddition = props.isAddition === undefined ? true : props.isAddition;
	
	const [reduce, setReduce] = useState(false);
	
	const [reduce2, setReduce2] = useState(false);
	
	const [reduce3, setReduce3] = useState(false);
	
	const [reduce4, setReduce4] = useState(false);
	
	const [reduceOtherProps, setReduceOtherProps] = useState(true);

	return (
		<div className="gedeonproperties">
			<div className="content">
			
				<div className="propertiesfield title textSizeXL bold">{isAddition ? "Add document" : "Properties"}</div>
				
				<div>
				
				
				<div className="propertiesfield docproperties">
					<div className="gedeonflex fieldTitle">
						<img 
							alt="reduce general properties tab"
							title="reduce general properties tab"
							src={reduce ? ICON_PLUS : ICON_MINUS}
							onClick={() => setReduce(!reduce)}
						/>
						<span>General Properties</span>
					</div>
					<div className={"propertiesfield-content"+(reduce ? " reduce" : "")}>
						<div className="gedeonflex propRow">
							<div className="gedeonflex property" style={{"flexGrow":8}}>
								<span className="label" style={{"flexGrow":2}}>Subject</span>
								<input style={{"flexGrow":10}} placeholder=""/>
							</div>
							<button style={{"flexGrow":4}}>Comments &amp; history</button>
						</div>
						<div className="gedeonflex">
							<div className="gedeonflex property">
								<span className="label">Document Author</span>
								<input placeholder=""/>
							</div>
							<div className="gedeonflex property">
								<span className="label">Document Date</span>
								<input placeholder=""/>
							</div>
							<div className="gedeonflex property">
								<span className="label">Document ID</span>
								<input placeholder=""/>
							</div>
						</div>
						<hr />
						<div className="gedeonflex">
							<div>
								<div className="gedeonflex property propRow">
									<span className="label">Document Owner</span>
									<input placeholder=""/>
									<img 
										alt="reduce general properties tab"
										title="reduce general properties tab"
										src={ICON_REFRESH}
										style={{width:"1.25rem", height:"1.25rem"}}
									/>
								</div>
								<div className="gedeonflex property">
									<span className="label">Workflow Unit</span>
									<input placeholder=""/>
								</div>
							</div>
							<div className="gedeoncolumn">
								<div className="gedeonflex propRow">
									<div className="gedeonflex property">
										<input className="label" type="checkbox"/>
										<span>Processed</span>
									</div>
									<div className="gedeonflex property">
										<input className="label" type="checkbox"/>
										<span className="textSizeS">Add to my followed documents</span>
									</div>
									<div className="gedeonflex property">
										<span className="label">Due Date</span>
										<input placeholder=""/>
									</div>
								</div>
								<div className="gedeonflex property">
									<span className="label">Workflow Ledger</span>
									<input placeholder=""/>
								</div>
							</div>
						</div>
						<hr />
						<div className="gedeonflex textSizeS">
							<div className="gedeonflex property">
								<span className="label">Business Sequence</span>
								<input placeholder=""/>
							</div>
							<div className="propertyspacer"/>
							<div className="gedeonflex property">
								<input className="label" type="checkbox"/>
								<span>Create a claim notification</span>
							</div>
						</div>
						<div className="otherproperties">
							<div className="gedeonflex subFieldTitle">
								<div className="gedeonflex">
									<img 
										alt="reduce general properties tab"
										title="reduce general properties tab"
										src={reduce ? ICON_PLUS : ICON_MINUS}
										onClick={() => setReduceOtherProps(!reduceOtherProps)}
									/>
									<span>Other Properties</span>
								</div>
								<hr />
							</div>
							<div className={"gedeonflex otherproperies_content" + (reduceOtherProps ? " reduce" : "")}>
								<div className="gedeonflex property">
									<span className="label">Document Source</span>
									<input placeholder="" disabled value="WebApp"/>
								</div>
								<div className="propertyspacer"/>
								<div className="gedeonflex property">
									<span className="label">Creation Date</span>
									<input placeholder="" disabled/>
								</div>
								<div className="propertyspacer"/>
								<div className="gedeonflex property">
									<span className="label">Added By</span>
									<input placeholder="" disabled/>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				
				<div className="gedeonflex">
				
					<div className="propertiesfield indexations">
						<div className="gedeonflex fieldTitle">
							<img 
								alt="reduce general properties tab"
								title="reduce general properties tab"
								src={reduce2 ? ICON_PLUS : ICON_MINUS}
								onClick={() => setReduce2(!reduce2)}
							/>
							<span>Indexations &amp; properties</span>
						</div>
						<div className={"propertiesfield-content"+(reduce2 ? " reduce" : "")}>
							<IndexationsProperties />
						</div>
					</div>
				
				
					<div className="propertiesfield currentindexations">
						<div className="gedeonflex fieldTitle">
							<img 
								alt="reduce general properties tab"
								title="reduce general properties tab"
								src={reduce2 ? ICON_PLUS : ICON_MINUS}
								onClick={() => setReduce2(!reduce2)}
							/>
							<span>Indexations &amp; properties</span>
						</div>
						<div className={"propertiesfield-content"+(reduce2 ? " reduce" : "")}>
						
							<div className="gedeoncolumn">
								<div className="gedeonflex actions">
									<button className="textSizeS">
										<img 
											alt="add new indexation"
											title="add new indexation"
											src={ICON_PASTE}
										/>
										Paste Index
									</button>
									<button className="textSizeS">
										<img 
											alt="add new indexation"
											title="add new indexation"
											src={ICON_CPLUS}
										/>
										Add new index
									</button>
								</div>
								
								<div className="gedeoncolumn currentindexation selected valid">
									<div className="gedeonflex title">
										<img 
											alt=" ??? "
											title=" ??? "
											src={ICON_COPY}
											style={{visibility:"hidden"}}
										/>
										<img 
											alt="copy indexation"
											title="copy indexation"
											src={ICON_COPY}
										/>
										<img 
											alt="duplicate indexation"
											title="duplicate indexation"
											src={ICON_CPLUS}
										/>
										<span className="bold">
											FAC
										</span>
										<div className="spacer"/>
										<img 
											alt="cdelete indexation"
											title="delete indexation"
											src={ICON_CLOSE}
										/>
									</div>
									<div className="gedeoncolumn content">
										<span>Facultative : FA008634</span>
										<span>Technical and underwriting document abcd efgh ijklm</span>
										<span>Negotiation - Administration</span>
									</div>
								</div>
							</div>
						
						</div>
					</div>
				</div>
				
				
				
				<div className="propertiesfield security">
					<div className="gedeonflex fieldTitle">
						<img 
							alt="reduce confidentiality tab"
							title="reduce confidentiality tab"
							src={reduce3 ? ICON_PLUS : ICON_MINUS}
							onClick={() => setReduce3(!reduce3)}
						/>
						<span>Confidentiality &amp; Restrictions</span>
					</div>
					<div className={"propertiesfield-content"+(reduce3 ? " reduce" : "")}>
						<Confidentiality />
					</div>
				</div>
				
				
				
				<div className="propertiesfield docfiles">
					<div className="gedeonflex fieldTitle">
						<img 
							alt="reduce involved documents tab"
							title="reduce involved documents tab"
							src={reduce4 ? ICON_PLUS : ICON_MINUS}
							onClick={() => setReduce4(!reduce4)}
						/>
						<span>Involved document(s)</span>
					</div>
					<div className={"propertiesfield-content"+(reduce4 ? " reduce" : "")}>
						
						<div className="gedeonflex">
							<img 
								alt="view document"
								title="view document"
								src={ICON_VIEW}
							/>
							<img 
								alt="download document"
								title="download document"
								src={ICON_DOWNLOAD}
							/>
							<img 
								alt="view document versions"
								title="view document versions"
								src={ICON_VERSIONS}
							/>
							<span>Test Document.pdf</span>
						</div>
						
						
					</div>
					
					
				</div>
				
				</div>
				
				
				
				
				
			</div>
			<div className="propertiesactions">
					<button>Cancel</button>
					<button>Save</button>
				</div>
		</div>
	);
	
}
export default Properties;