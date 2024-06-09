import ProtectedRoute from '../components/ProtectedRoute';


export default class RouteCreator {
  constructor(backendAddress, defaultFailurePage=null) {
    this.backendAddress = backendAddress 
    this.defaultFailurePage = defaultFailurePage 
  };

  createRoute(givenPath, givenElement) {
    return {
      path: givenPath,
      element: givenElement
    };
  }

  createProtectedRoute(
    givenPath, 
    successChild, 
    failureChild
  ) {
    if (failureChild === undefined) {
      failureChild = this.defaultFailurePage
    }
    return this.createRoute(
      givenPath, <ProtectedRoute 
                  backendUrl={this.backendAddress + givenPath}
                  successChild={successChild}
                  failureChild={failureChild}/>
    );
  }

}

