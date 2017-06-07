/**
 * Created by tanner on 6/2/17.
 */
import React, {Component} from 'react';

class ViewRow extends Component
{
    constructor(props)
    {
        super(props);
        this.display = props.display;
    }

    render()
    {
        return (
            <div className="row">
                <index>{this.display}</index>
                <p>{this.display}</p>
                <p>&gt;</p>
            </div>
        );
    }
}

export default ViewRow