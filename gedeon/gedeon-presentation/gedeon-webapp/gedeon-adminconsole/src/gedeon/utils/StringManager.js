class StringManager {
	/**
	* Get Text Width
	*/
	static getTextWidth(text,textSize) {
  
		let textNode = document.createElement("span");
		document.body.appendChild(textNode);

		textNode.style.font = "times new roman";
		textNode.style.fontSize = textSize + "px";
		textNode.style.height = 'auto';
		textNode.style.width = 'auto';
		textNode.style.position = 'absolute';
		textNode.style.whiteSpace = 'no-wrap';
		textNode.innerHTML = text;

		let width = Math.ceil(textNode.clientWidth);
		//formattedWidth = width + "px";

		document.body.removeChild(textNode);
		return width;
    }
}
export default StringManager