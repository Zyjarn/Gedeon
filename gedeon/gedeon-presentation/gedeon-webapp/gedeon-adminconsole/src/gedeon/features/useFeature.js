import useSearch from './useSearch';
import useFavorites from './useFavorites';
import useFolder from './useFolder';
import useFeatureTreeview from './useFeatureTreeview';

export function useFeature(feature) {

	let name = "";
	if(feature !== undefined && feature !== null){
		name = feature.name;
	} else {
		feature = {};
	}
	
	let active = " active";
	
	return (
		<div className="features">
			<div className={"feature" + (name === "search" ? active : "")}>
			{useSearch(feature)}
			</div>
			<div className={"feature" + (name === "favorites" ? active : "")}>
			{useFavorites(feature === undefined ? {} : feature)}
			</div>
			<div className={"feature" + (name === "folder" ? active : "")}>
			{useFolder(feature === undefined ? {} : feature)}
			</div>
			<div className={"feature" + (name === "treeview" ? active : "")}>
			{useFeatureTreeview(feature === undefined ? {} : feature)}
			</div>
		</div>
	)
}
export default useFeature;