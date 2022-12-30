import { useState } from 'react';

export function Modal(props) {
	
	let actions = props.actions === undefined ? [] : props.actions;

	return (
		<div className={"modal_holder"+(props.isActive ? " active": "")}  onClick={() => props.onClose()}>
			<div className="h column center marginauto">
				<div className="modal column bordered">
					<div className="modal_title textSizeL bold">
						{props.title}
					</div>
					<div className="yover">
						{props.content}
					</div>
					<div className="modal_actions flex">
						{
							actions.map((obj,index) => (<button key={index} onClick={() => obj.onClick()}>{obj.label}</button>))
						}
						<div className="space" />
						<button onClick={() => props.onClose()}>Close</button>
					</div>
				</div>
			</div>
		</div>
	);
	
}
export default Modal;