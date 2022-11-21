class ResultSet {
	/**
	*
	*/
	constructor(props){
		if(props === undefined){
			this.columns = [ 
				{"name":"id", "type":"string", "label":"Id", "isHidden":true, "isSortable":false},
				{"name":"mimetype", "type":"string", "label":"MimeType", "isStateIcon":true, "filter":"select"},
				{"name":"name", "type":"string", "label":"Name", "filter":"string"},
				{"name":"nature", "type":"string", "label":"Nature", "filter":"string"},
				{"name":"type", "type":"string", "label":"Type", "filter":"string"},
				{"name":"size", "type":"long", "label":"Content Size", "filter":"long"}
			];
			
			var item = {
					"attributes":{
						"id":"ABC123",
						"mimetype":"application/pdf",
						"name":"Test.pdf",
						"nature":"Technical",
						"type":"test",
						"size":1048576
					},
					"attributesDisplay":{
						"size":"1MB"
					}
				};
			this.items = [];
			
			Array.from({length:155}).map((obj) => {
				this.items.push(item);
			});
			
			this.pageSize=50;
		}else {
			this.columns = props.columns ;
			this.items = props.items;
			this.pageSize = props.pageSize;
			
			if(this.columns === undefined){
				this.columns = ["Hello","Bubu"];
			}
			if(this.items === undefined){
				this.items = [["A","B"]];
			}
			if(this.pageSize === undefined){
				this.pageSize = 50;
			}
		}
	}
}
export default ResultSet