import { useState } from 'react';

export function Breadcrumb(props) {
/* &#8230; */
	let tree = props.tree === undefined ? {} : props.tree;
	let limit = props.limit === undefined ? 15 : props.limit;
	let nodes = [];
	
	function fillNodes(node,nodes){
		if(node.selected){
			nodes.push(node);
			return true;
		} else if (node.children !== undefined){
			for(let i=0; i < node.children.length; i++){
				if(fillNodes(node.children[i],nodes)){
					nodes.push(node);
					return true;
				}
			}
		}
		return false;
	}

	fillNodes(tree,nodes);
	nodes.reverse();

	let displayNodes = [(<div key={"root"} className="bread-item" >/</div>)];
	for(let i in nodes){
		if(parseInt(i) !== (nodes.length-1)) {
			let label = nodes[i].label.length > limit ? nodes[i].label.substr(0,limit)+"…" : nodes[i].label;
			displayNodes.push(<div className="bread-item activable" title={nodes[i].label} key={i}>{label}</div>);
			displayNodes.push((<div key={"slash"+i} className="bread-item" >/</div>));
		}else {
			displayNodes.push(<div className="bread-item" title={nodes[i].label} key={i}>{nodes[i].label}</div>);
		}
	}

	return (
		<div className="breadcrumb flex">
		{
			displayNodes
		}
		</div>
	);
	
}
export default Breadcrumb;