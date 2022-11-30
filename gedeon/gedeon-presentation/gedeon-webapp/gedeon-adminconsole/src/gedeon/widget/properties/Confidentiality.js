import {useState} from 'react';

import CLOSE_ICON from '../../../images/close.svg';

export function Confidentiality(props) {
	
	const [confidentiality,setConfidentiality] = useState(0);
	const [restriction,setRestriction] = useState(0);
	const [communities,setCommunities] = useState({
		selected: [],
		available:[
			{
				label:"Test",
				value:1
			},
			{
				label:"Archivage",
				value:2
			}
			]
	});

	/**
	* Restriction list dependening of confidentiality value
	*/
	let restrictionOpts = [<option key={0} value={0}></option>];
	if(confidentiality === 2) {
		 restrictionOpts = [<option value={2}>Internal</option>];
	} else if(confidentiality === 3){
		restrictionOpts = [
			<option value={3}>Units</option>,
			<option value={4}>Communities</option>,
			<option value={5}>Units and Communities</option>
		];
	}
	
	/**
	* Communities list depending of restriction value
	*/
	let classNameCommunities = "confidentiality-item communities";
	let communitiesValue = 0;
	if(restriction > 3){
		classNameCommunities += " active";
	}	
	let communitiesOpts = [<option value=""></option>];
	communities.available.forEach((community, index) => communitiesOpts.push(<option key={index} value={community.value}>{community.label}</option>));
	
	/**
	* event on select communities
	*/
	function onAddCommunity(communityId){
		let newCommunitiesAvailable = communities.available.map((x) => x);
		let community = newCommunitiesAvailable.splice(newCommunitiesAvailable.findIndex((x) => x.value === communityId),1)[0];
		let newCommunitiesSelected = communities.selected.map((x) => x);
		newCommunitiesSelected.push(community);
		
		setCommunities({
			selected: newCommunitiesSelected,
			available:newCommunitiesAvailable
		});
	}
	
		/**
	* event on select communities
	*/
	function onRemoveCommunity(communityId){
		let newCommunitiesSelected = communities.selected.map((x) => x);
		let community = newCommunitiesSelected.splice(newCommunitiesSelected.findIndex((x) => x.value === communityId),1)[0];
		let newCommunitiesAvailable = communities.available.map((x) => x);
		newCommunitiesAvailable.push(community);
		
		setCommunities({
			selected: newCommunitiesSelected,
			available:newCommunitiesAvailable
		});
	}


	return (
		<div className="confidentiality">
			<div className="confidentiality-item">
				<span>Confidentiality</span>
				<select value={confidentiality} onChange={(e) => setConfidentiality(parseInt(e.target.value))}>
					<option value={0}></option>
					<option value={2}>Internal</option>
					<option value={3}>Confidential</option>
				</select>
			</div>
			<div className="confidentiality-item">
				<span>Restriction</span>
				<select value={restriction} onChange={(e) => setRestriction(parseInt(e.target.value))}>
				{restrictionOpts}
				</select>
			</div>
			<div className={classNameCommunities}>
				<span>Communities</span>
				<select onChange={(e) => {if(e.target.value !== "")onAddCommunity(parseInt(e.target.value))}} value="">
					<option value=""></option>
					{
						communities.available.map((community, index) => (<option key={index} value={community.value}>{community.label}</option>))
					}
				</select>
				<ul>
				{
					communities.selected.map((community, index) => (
						<li className="listselected" key={index} value={community.value}>
							{community.label}
							<img 
								alt={"remove community "+community.label}
								title={"remove community "+community.label}
								src={CLOSE_ICON}
								onClick={() => onRemoveCommunity(community.value)}
							/>
						</li>))
				}
				</ul>
			</div>
		</div>
	);
	
}
export default Confidentiality;