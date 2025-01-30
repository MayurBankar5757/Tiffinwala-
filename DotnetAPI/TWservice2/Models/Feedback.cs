using System;
using System.Collections.Generic;

namespace TWservice2.Models
{
    public partial class Feedback
    {
        public int FeedbackId { get; set; }
        public int CustomerPlanId { get; set; }
        public int Uid { get; set; }
        public int VSubscriptionId { get; set; }
        public string FeedbackText { get; set; } = null!;
        public int? Rating { get; set; }
        public DateOnly FeedbackDate { get; set; }

        public virtual CustomerSubscribedPlan CustomerPlan { get; set; } = null!;
        public virtual User UidNavigation { get; set; } = null!;
        public virtual VendorSubscriptionPlan VSubscription { get; set; } = null!;
    }
}
