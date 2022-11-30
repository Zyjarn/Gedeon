import React, { Component } from 'react';

import './GedeonFileUpload.css';

class GedeonFileUpload extends Component {

	constructor(props) {
		super(props);
		this.callBackFileUpload=props.handleFileUpload;
		this.state = {
		  isDragOver: false
		};
	}

	componentDidMount() {

	}
	
	/**
	* Prevent default and propagation of given event
	*/
	prevent = (e) => {
		if(e !== undefined){
			e.preventDefault();
			e.stopPropagation();
		}
	}
	
	/**
	* Event when drag file in zone start, prevent all default browser behavior
	*/
	dragStart = (e) => {
		this.prevent(e);
	}
	
	/**
	* Event when file enter the drag and drop zone
	*/
	dragHover = (e) => {
		this.prevent(e);
		this.setState({ isDragOver: true });
	}
	
	/**
	* Event when file leave the drag and drop zone
	*/
	dragEnd = (e) => {
		this.prevent(e);
		this.setState({ isDragOver: false });
	}
	
	/**
	* Event when drag and drop is complete
	*/
	handleFileUpload = (e) => {
		this.prevent(e);
		this.dragEnd();
		//Send files to parent component
		this.callBackFileUpload(e.dataTransfer.files);
	}

	/**
	* Render component
	*/
	render() {

		return (
			<form 
				className={"box "+(this.state.isDragOver ? "is-dragover":"")} 
				method="post" 
				action=""
				onDrag={this.dragStart}
				onDragStart={this.dragStart}
				onDragOver={this.dragHover}
				onDragEnter={this.dragHover}
				onDragEnd={this.dragEnd}
				onDragLeave={this.dragEnd}
				onDrop={this.handleFileUpload}
				encType="multipart/form-data"
			>
			  <div className="box__input">
				<img
					alt=""
					src="/upload.png"
					width="30"
					height="30"
					className="box__icon"
				/>
				<input className="box__file" type="file" name="files[]" id="file" data-multiple-caption="{count} files selected" multiple />
				<label htmlFor="file">
					<strong>Choose a file</strong>
					<span className="box__dragndrop"> or drag it here</span>.
				</label>
				<button className="box__button" type="submit">Upload</button>
			  </div>
			  <div className="box__uploading">Uploading…</div>
			  <div className="box__success">Done!</div>
			  <div className="box__error">Error! <span></span>.</div>
			</form>
		);
	}
}
export default GedeonFileUpload;