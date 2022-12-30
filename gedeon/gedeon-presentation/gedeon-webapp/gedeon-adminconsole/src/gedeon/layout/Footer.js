
import ICON_CLOSE from '../../images/close.svg';

/**
 * Footer element of app with a list of reduced properties view that can be close or reopened
 * @param {reducedElements:[], onCloseElement:function(), onOpenElement:function()} props 
 * @returns 
 */
export function Footer(props) {

    let elts = props.reducedElements === undefined ? [] : props.reducedElements;

    const onClickClose = (event, elt) => {
        event.stopPropagation();
        props.onCloseElement(elt);
    };

	return (
		<div className="flex-reverse wrap">
        {
            elts.map((obj,index) => (
                <div key={index} className="flex center bordered-object" onClick={() => props.onOpenElement(obj)}>
                    <img
                        alt="X"
                        title="close properties view."
                        src={ICON_CLOSE}
                        className="activeIcon activehover"
                        onClick={(event) => onClickClose(event, obj)}
                    />
                    <i>{obj.label}</i>
                </div>
            ))
        }
        </div>
	);
	
}
export default Footer;