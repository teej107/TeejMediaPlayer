/**
 * Created by tanner on 6/6/17.
 */
class ViewHistory
{
    constructor()
    {
        var history = [];

        this.isRoot = () =>
        {
            return history.length === 0;
        };

        this.up = (view, obj) =>
        {
            var push = {view: view.toLowerCase(), search: obj};
            history.push(push);
            return push;
        };

        this.current = () =>
        {
            if (this.isRoot())
                return {view: "root"};
            return history[history.length - 1];
        };

        this.back = () =>
        {
            history.pop();
            return this.current();
        };
    }
}

export default ViewHistory;