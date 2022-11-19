class ResultSet {
	/**
	*
	*/
	constructor(props){
		if(props == undefined){
			this.columns = ["Hello","Bubu AU RHUM"];
			this.items = [["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"],["A","B","C"]];
			this.pageSize=50;
		}else {
			this.columns = props.columns ;
			this.items = props.items;
			this.pageSize = props.pageSize;
			
			if(this.columns == undefined){
				this.columns = ["Hello","Bubu"];
			}
			if(this.items == undefined){
				this.items = [["A","B"]];
			}
			if(this.pageSize == undefined){
				this.pageSize = 50;
			}
		}
	}
}
export default ResultSet